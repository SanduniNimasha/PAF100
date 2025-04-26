package com.PAF.paf.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PAF.paf.model.User;
import com.PAF.paf.model.LearningProgress;
import com.PAF.paf.repo.UserRepository;
import com.PAF.paf.repo.LearningProgressRepository;
import com.PAF.paf.service.LearningProgressService;

@Service
public class LearningProgressServiceImpl implements LearningProgressService {

    @Autowired
    private LearningProgressRepository learningProgressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<LearningProgress> getAllLearningProgresses() {
        return learningProgressRepository.findAll();
    }

    @Override
    public Optional<LearningProgress> getLearningProgressById(String id) {
        return learningProgressRepository.findById(id);
    }

    @Override
    public LearningProgress createLearningProgress(LearningProgress learningProgress) {
        Optional<User> userOptional = userRepository.findById(learningProgress.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            learningProgress.setUserId(user.getId());
            learningProgress.setUsername(user.getName());
            learningProgress.setUserProfile(user.getProfileImage());
            return learningProgressRepository.save(learningProgress);
        } else {
            return null;
        }
    }

    @Override
    public LearningProgress updateLearningProgress(String progressId, LearningProgress learningProgress) {
        if (learningProgressRepository.existsById(progressId)) {
            Optional<User> userOptional = userRepository.findById(learningProgress.getUserId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                learningProgress.setUserId(user.getId());
                learningProgress.setUsername(user.getName());
                learningProgress.setUserProfile(user.getProfileImage());
                learningProgress.setProgressId(progressId); // Make sure the model field is named 'progressId'
                return learningProgressRepository.save(learningProgress);
            }
        }
        return null;
    }

    @Override
    public void deleteLearningProgress(String progressId) {
        learningProgressRepository.deleteById(progressId);
    }
}
