package hr.asc.appic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.asc.appic.persistence.repository.OfferRepository;
import hr.asc.appic.persistence.repository.StoryRepository;
import hr.asc.appic.persistence.repository.UserRepository;
import hr.asc.appic.persistence.repository.WishRepository;

@Service
public class RepoProvider {

    @Autowired
    public UserRepository userRepository;
    @Autowired
    public WishRepository wishRepository;
    @Autowired
    public OfferRepository offerRepository;
    @Autowired
    public StoryRepository storyRepository;
}
