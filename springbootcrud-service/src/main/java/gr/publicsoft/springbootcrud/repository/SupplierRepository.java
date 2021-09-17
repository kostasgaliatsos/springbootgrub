//Supplier Repository interface able to handle CRUD requests
//and search functions by company name and vat number

package gr.publicsoft.springbootcrud.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

import gr.publicsoft.springbootcrud.model.Supplier;

@CrossOrigin(origins = "http://localhost:9000")
@RepositoryRestResource
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
  
  List<Supplier> findByCompanyName(String companyName);

  Supplier findByVatNumber(String vatNumber);

  // kostas: Implement a findByQuery function 
  // to be used by the frontend to search for suppliers
  // based on the mandatory fields: companyName and vatNumber

  // kostas: Thought: Because we are are using "LIKE (% <term> %)" in the query we are 
  // doing a full text search which is slow and resource hungry.
  // If we could change it to "LIKE ( <term> %)" so that it searches based on the first letters/numbers
  // we could index the columns companyName and vatNumber
  @Query("SELECT s FROM Supplier s" 
        + " WHERE s.companyName LIKE CONCAT('%', ?1, '%') "
        + " OR s.vatNumber LIKE CONCAT('%', ?1, '%')" )
  Page<Supplier> findByQuery(@Param("query") String query, Pageable pageable);
}
