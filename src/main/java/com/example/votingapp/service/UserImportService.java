package com.example.votingapp.service;

import com.example.votingapp.model.User;
import com.example.votingapp.repository.UserRepository;
import org.apache.commons.csv.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;

@Service
public class UserImportService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> importUsersFromCSV(MultipartFile file) throws IOException {
        List<User> users = new ArrayList<>();
        
        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                 .withFirstRecordAsHeader()
                 .withIgnoreHeaderCase()
                 .withTrim())) {
            
            for (CSVRecord record : csvParser) {
                User user = new User();
                user.setUserId(record.get("userId"));
                user.setPassword(passwordEncoder.encode(record.get("password")));
                user.setAdmin(Boolean.parseBoolean(record.get("isAdmin")));
                user.setCanVote(!user.isAdmin());
                
                users.add(user);
            }
        }
        
        return userRepository.saveAll(users);
    }
}