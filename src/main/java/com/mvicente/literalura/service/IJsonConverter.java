package com.mvicente.literalura.service;

import java.util.List;

public interface IJsonConverter {
     <T>T getData(String json, Class<T> clazz);

}
