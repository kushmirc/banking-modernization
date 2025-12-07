package com.banking.service.impl;

import com.banking.dto.NewsDTO;
import com.banking.service.NewsService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Override
    public List<NewsDTO> getAllNews() {
        List<NewsDTO> newsDtos = new ArrayList<>();
        //Path bankRatePath = Paths.get("src/main/resources/static/BankRate.txt");

        try{
            InputStream is = getClass().getResourceAsStream("/static/BankRate.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while((line = reader.readLine()) != null){
                int lastClose = line.lastIndexOf("...");
                int lastOpen = line.lastIndexOf(":", lastClose);

                String title=line.substring(0, lastOpen).trim() ;
                String desc= line.substring(lastOpen + 1, lastClose).trim() ;
                String link=line.substring(lastClose + 3).trim();

                NewsDTO newsDto = new NewsDTO(title, desc, link);
                newsDtos.add(newsDto);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return newsDtos;
    }
}
