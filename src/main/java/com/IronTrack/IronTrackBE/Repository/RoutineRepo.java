package com.IronTrack.IronTrackBE.Repository;

import com.IronTrack.IronTrackBE.Repository.Entities.RoutineEntity;
import com.IronTrack.IronTrackBE.Repository.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RoutineRepo extends JpaRepository<RoutineEntity, Integer> {
    Optional<List<RoutineEntity>> findAllByUserEntity(UserEntity userEntity);

}
