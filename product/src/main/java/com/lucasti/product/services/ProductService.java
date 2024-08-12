package com.lucasti.product.services;


import com.lucasti.product.dtos.ProductDto;
import com.lucasti.product.dtos.ProductUpdateRequestDto;
import com.lucasti.product.dtos.kafka.producer.ProductProducerDTO;
import com.lucasti.product.dtos.responses.ProductsReponseDTO;
import com.lucasti.product.entities.Departament;
import com.lucasti.product.entities.Product;
import com.lucasti.product.exceptions.DepartamentNotFoundException;
import com.lucasti.product.exceptions.ImageNotConverterException;
import com.lucasti.product.exceptions.ProductNotFoundException;
import com.lucasti.product.repositories.DepartamentRepository;
import com.lucasti.product.repositories.ProductRepository;
import com.lucasti.product.specifications.ProductSpecifications;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.lucasti.product.specifications.ProductSpecifications.filterProductByPriceOver;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    DepartamentRepository departamentRepository;
    
    @Autowired
    KafkaTemplate<String, ProductProducerDTO> kafkaTemplate;

    @Value("${kafka.topics.product}")
    String productTopic;

//    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor(); // Usando virtual threads
//    private final ExecutorService executorService = Executors.newFixedThreadPool(6); //Usando threads 

    public Product saveProduct(ProductDto productDto){
        //Sortgroup
//        Sort sort = Sort.by("description").ascending()
//                .and(Sort.by("price")).descending();

        Departament departament = departamentRepository.findById(productDto.departmentId())
                .orElseThrow(() -> new DepartamentNotFoundException("Departament not found: "+ productDto.departmentId()));
        var product = ProductDto.toProduct(productDto);
        product.setDepartament(departament);
         productRepository.save(product);
        sendProductToTopic(product);
        return product;
    }



    public Product getProductById(UUID uuid){
      return productRepository.findById(uuid).orElseThrow(() -> new ProductNotFoundException("Product not found: "+ uuid));
    }


    public Product updateProduct(Product productDataBase, ProductUpdateRequestDto productDto){
        BeanUtils.copyProperties(productDto, productDataBase);
        try {
            productDataBase.setImage(productDto.multipartFile().getBytes());
        } catch (IOException e) {
            throw new ImageNotConverterException(e.getMessage());
        }
        return productRepository.save(productDataBase);
    }


    @Cacheable(value = "products")
    public Page<ProductsReponseDTO> getAllProducts(Pageable pageable, Specification<Product> specification) {
        Page<Product> productPage = productRepository.findAll(specification, pageable);

        List<ProductsReponseDTO> dtoList = productPage.stream()
                  .map(ProductsReponseDTO::toProductResponseDTO)
                  .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, productPage.getTotalElements());
    }


    //SPECIFICATIONS
    public Page<Product> findProductsCriteria(String departamentName, String description, Double price, Pageable pageable) {
        Specification<Product> spec = ProductSpecifications
                .filterByDepartamentAndDescription(departamentName, description)
                .and(filterProductByPriceOver(price));
        return productRepository.findAll(spec, pageable);
    }




    public Page<Product> getAllProductAsync(Pageable pageable){
        return productRepository.findAll(pageable);
    }


    public Page<Product> getAllVirtual(Pageable pageable) {
        // Criar um CompletableFuture para gerenciar a execução assíncrona
        CompletableFuture<Page<Product>> future = new CompletableFuture<>();

        // Iniciar a thread virtual para executar a tarefa de busca de produtos
        Thread.startVirtualThread(() -> {
            try {
                Page<Product> page = productRepository.findAll(pageable);
                future.complete(page); // Completar o CompletableFuture com o resultado
            } catch (Exception e) {
                future.completeExceptionally(e); // Completar o CompletableFuture com exceção em caso de erro
            }
        });

        // Bloquear até a tarefa ser concluída e retornar o resultado
        return future.join();
    }

    private void sendProductToTopic(Product product) {
        String status = Optional.ofNullable(product.getStatus())
                .map(Enum::toString)
                .orElse("UNKNOWN");

        var productBuild = ProductProducerDTO.builder()
                .id(product.getId())
                .price(product.getPrice())
                .status(status)
                .sku(product.getSkuSecret())
                .description(product.getDescription())
                .createAt(product.getCreateAt())
                .build();

        CompletableFuture<SendResult<String, ProductProducerDTO>> cfKafka = kafkaTemplate.send(productTopic, productBuild);

        cfKafka.whenComplete((result, ex) -> {
            if(ex == null){
                var offset = result.getRecordMetadata().offset();
                var partition = result.getRecordMetadata().partition();


                System.out.println("Sent Message = "+ productBuild +
                        ", offset = " + offset +
                        ", partition = "+ partition);
            } else {
                System.out.println(ex.getMessage());
            }

        });
    }
}
