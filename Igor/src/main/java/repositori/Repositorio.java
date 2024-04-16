package repositori;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Employe.*;

@Repository
public interface Repositorio extends JpaRepository<Employee, Integer> {

	public List<Employee> findByActive(Boolean active);

}
