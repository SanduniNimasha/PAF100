package com.PAF.paf.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PAF.paf.model.User;
import com.PAF.paf.model.LearningPlan;
import com.PAF.paf.repo.UserRepository;
import com.PAF.paf.repo.LearningPlanRepository;
import com.PAF.paf.service.LearningPlanService;

@Service
public class LearningPlanServiceImpl implements LearningPlanService {

    @Autowired
    private LearningPlanRepository learningPlanRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<LearningPlan> getAllLearningPlans() {
        return learningPlanRepository.findAll();
    }

    @Override
    public Optional<LearningPlan> getLearningPlanById(String statusId) {
        return learningPlanRepository.findById(statusId);
    }

    @Override
    public LearningPlan createLearningPlan(LearningPlan learningPlan) {
        Optional<User> userOptional = userRepository.findById(learningPlan.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            learningPlan.setUserId(user.getId());
            learningPlan.setUsername(user.getName());
            learningPlan.setUserProfile(user.getProfileImage());
            return learningPlanRepository.save(learningPlan);
        } else {
            return null;
        }
    }

    @Override
    public LearningPlan updateLearningPlan(String statusId, LearningPlan learningPlan) {
        if (learningPlanRepository.existsById(statusId)) {
            Optional<User> userOptional = userRepository.findById(learningPlan.getUserId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                learningPlan.setUserId(user.getId());
                learningPlan.setUsername(user.getName());
                learningPlan.setUserProfile(user.getProfileImage());
                learningPlan.setStatusId(statusId);
                return learningPlanRepository.save(learningPlan);
            }
        }
        return null;
    }

    @Override
    public void deleteLearningPlan(String statusId) {
        learningPlanRepository.deleteById(statusId);
    }
}
