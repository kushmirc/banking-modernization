package com.banking.service;

import com.banking.dto.NewsDto;

import java.util.List;

public interface NewsService {
    List<NewsDto> getAllNews();
}
