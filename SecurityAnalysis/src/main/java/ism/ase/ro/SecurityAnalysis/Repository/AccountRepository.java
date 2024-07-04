package ism.ase.ro.SecurityAnalysis.Repository;

import ism.ase.ro.SecurityAnalysis.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account getAccountByAccountOwner(Long accountOwner);
}