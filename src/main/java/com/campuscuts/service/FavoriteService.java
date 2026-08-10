package com.campuscuts.service;

import com.campuscuts.entity.Favorite;
import com.campuscuts.entity.Provider;
import com.campuscuts.entity.User;
import com.campuscuts.repository.FavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public List<Favorite> forUser(User user) {
        return favoriteRepository.findByUserId(user.getId());
    }

    public boolean isFavorited(User user, Provider provider) {
        return favoriteRepository.existsByUserIdAndProviderId(user.getId(), provider.getId());
    }

    @Transactional
    public boolean toggle(User user, Provider provider) {
        if (favoriteRepository.existsByUserIdAndProviderId(user.getId(), provider.getId())) {
            favoriteRepository.deleteByUserIdAndProviderId(user.getId(), provider.getId());
            return false;
        }
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProvider(provider);
        favoriteRepository.save(favorite);
        return true;
    }
}
