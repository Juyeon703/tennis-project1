package tennis.project.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tennis.project.domain.Club;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class ClubRepositoryImpl implements ClubRepositoryInterface {

    @PersistenceContext
    private final EntityManager em;

    public Club findOne(Long clubId){
        return em.find(Club.class, clubId);
    }

}
