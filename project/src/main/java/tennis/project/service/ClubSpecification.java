package tennis.project.service;

import org.springframework.data.jpa.domain.Specification;
import tennis.project.domain.Club;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ClubSpecification {

  public enum SearchKey {
    LOCAL("local"),
    NAME("name"),
    STATUS("status");
    private final String title;

    SearchKey(String title) {
      this.title = title;
    }

    public String getTitle() {
      return title;
    }
  }

  public static Specification<Club> searchWith(Map<SearchKey, Object> searchKeyword) {
    return (Specification<Club>) ((root, query, builder) -> {
      List<Predicate> predicate = getPredicateWithKeyword(searchKeyword, root, builder);
      return builder.and(predicate.toArray(new Predicate[0]));
    });
  }

  private static List<Predicate> getPredicateWithKeyword(Map<SearchKey, Object> searchKeyword, Root<Club> root, CriteriaBuilder builder) {
    List<Predicate> predicate = new ArrayList<>();
    for (SearchKey key : searchKeyword.keySet()) {
      switch (key) {
        case LOCAL:
        case NAME:
          predicate.add(builder.equal(
            root.get(key.title),searchKeyword.get(key)
          ));
          break;
        case STATUS:
          predicate.add(builder.greaterThan(
            root.get(key.title), Integer.valueOf(searchKeyword.get(key).toString())
          ));
          break;
      }
    }
    return predicate;
  }

  public static Specification<Club> equlLocal(String localName) {
    return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("local").get("name"), localName));
  }

  public static Specification<Club> equlStatus(String status) {
    return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status").get("title"), status));
  }

  public static Specification<Club> equlName(String clubName) {
    return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), clubName));
  }
}
