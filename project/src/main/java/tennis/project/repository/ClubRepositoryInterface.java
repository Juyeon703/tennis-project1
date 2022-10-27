package tennis.project.repository;


import tennis.project.domain.Club;

public interface ClubRepositoryInterface {

    Club findOne(Long clubId);
}
