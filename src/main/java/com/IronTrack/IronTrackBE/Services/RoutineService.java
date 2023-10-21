package com.IronTrack.IronTrackBE.Services;

import com.IronTrack.IronTrackBE.Repository.Entities.RoutineEntity;
import com.IronTrack.IronTrackBE.Repository.Entities.RoutineExercisesEntity;
import com.IronTrack.IronTrackBE.Repository.Entities.UserEntity;
import com.IronTrack.IronTrackBE.Repository.RoutineExercisesRepo;
import com.IronTrack.IronTrackBE.Repository.RoutineRepo;
import com.IronTrack.IronTrackBE.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoutineService {

    private final UserRepo userRepo;
    private final RoutineRepo routineRepo;
    private final RoutineExercisesRepo routineExercisesRepo;

    public RoutineExercisesEntity getRoutineExercise(Long routineId, Long routineExerciseId) {
        RoutineEntity routine;
        RoutineExercisesEntity routineExercise;

        try {
            routine = getRoutineEntity(routineId);
            routineExercise = getRoutineExercisesEntity(routine, routineExerciseId);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        } catch (SecurityException e) {
            throw new SecurityException(e.getMessage());
        }

        return routineExercise;

    }

    private UserEntity getUserEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {

            return userRepo.findByEmail(auth.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Invalid user email"));
        }

        throw new SecurityException("User is not authenticated");
    }

    private RoutineEntity getRoutineEntity(Long routineId) throws NullPointerException, SecurityException {

        UserEntity user = getUserEntity();
        UserEntity routineUser;
        RoutineEntity routine = routineRepo.findById(routineId);

        try {
            routineUser = routine.getUserEntity();
        } catch (NullPointerException e) {
            throw new NullPointerException("Routine was not found");
        }

        if (routineUser != user) {
            throw new SecurityException("User does not own routine");
        }

        return routine;
    }

    private RoutineExercisesEntity getRoutineExercisesEntity(RoutineEntity routine, Long routineExerciseId) throws NullPointerException, SecurityException {
        RoutineExercisesEntity routineExercise = routineExercisesRepo.findById(routineExerciseId);
        RoutineEntity routineRef;

        try {
            routineRef = routineExercise.getRoutineEntity();
        } catch (NullPointerException e) {
            throw new NullPointerException("Routine Exercise not found");
        }

        if (routineRef != routine) {
            throw new SecurityException("User does not own routine exercise");
        }

        return routineExercise;
    }
}
