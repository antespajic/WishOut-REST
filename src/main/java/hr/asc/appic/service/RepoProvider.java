package hr.asc.appic.service;

import hr.asc.appic.persistence.repository.StoryRepository;
import hr.asc.appic.persistence.repository.UserRepository;
import hr.asc.appic.persistence.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepoProvider {

    @Autowired
    public UserRepository userRepository;
    @Autowired
    public WishRepository wishRepository;
    @Autowired
    public StoryRepository storyRepository;
}
