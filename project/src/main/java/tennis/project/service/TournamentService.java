package tennis.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tennis.project.domain.Local;
import tennis.project.domain.Tournament;
import tennis.project.repository.LocalRepository;
import tennis.project.repository.TournamentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TournamentService {

  private final TournamentRepository tournamentRepository;
  private final LocalRepository localRepository;

  public List<Tournament> getTournamentList() {
    return tournamentRepository.findAll();
  }

  public List<Local> getLocalList() {
    return localRepository.findAll();
  }
}
