package com.lucasti.product.exceptions;

public class ImageNotConverterException  extends  RuntimeException{

    public ImageNotConverterException(String message){
        super(message);
    }

    public ImageNotConverterException(){
        super("Image not converter");
    }
}
