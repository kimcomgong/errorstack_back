package kimce.kyj.errorstack.repositories;

import kimce.kyj.errorstack.models.Stack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StackRepository extends JpaRepository<Stack, Long> {
}
