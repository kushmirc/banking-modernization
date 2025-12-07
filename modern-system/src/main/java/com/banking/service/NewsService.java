package com.banking.service;

import com.banking.dto.NewsDTO;

import java.util.List;

public interface NewsService {
    List<NewsDTO> getAllNews();
}
