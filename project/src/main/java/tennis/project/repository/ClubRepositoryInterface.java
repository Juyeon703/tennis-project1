package tennis.project.repository;


import tennis.project.domain.Club;

import java.util.Optional;

public interface ClubRepositoryInterface {

    Club findOne(Long clubId);
}
