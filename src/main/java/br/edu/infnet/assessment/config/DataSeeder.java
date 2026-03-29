package br.edu.infnet.assessment.config;

import br.edu.infnet.assessment.model.*;
import br.edu.infnet.assessment.enums.ClasseAventureiro;
import br.edu.infnet.assessment.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;

@Component
public class DataSeeder implements CommandLineRunner {

    private final OrganizacaoRepository orgRepo;
    private final UsuarioRepository userRepo;
    private final AventureiroRepository aventureiroRepo;

    public DataSeeder(OrganizacaoRepository orgRepo, UsuarioRepository userRepo, AventureiroRepository aventureiroRepo) {
        this.orgRepo = orgRepo;
        this.userRepo = userRepo;
        this.aventureiroRepo = aventureiroRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        // Só popula se o banco estiver vazio
        if (orgRepo.count() == 0) {
            Organizacao org = new Organizacao();
            org.setNome("Guilda de Teste");
            org.setAtivo(true);
            org.setCreatedAt(OffsetDateTime.now());
            org = orgRepo.save(org);

            Usuario user = new Usuario();
            user.setOrganizacao(org);
            user.setNome("Admin Mestre");
            user.setEmail("admin@guilda.com");
            user.setSenhaHash("123456"); // Apenas para teste
            user.setCreatedAt(OffsetDateTime.now());
            user = userRepo.save(user);

            Aventureiro adv1 = new Aventureiro();
            adv1.setOrganizacao(org);
            adv1.setUsuarioResponsavel(user);
            adv1.setNome("Aragorn");
            adv1.setClasse(ClasseAventureiro.GUERREIRO);
            adv1.setNivel(10);
            adv1.setAtivo(true);
            aventureiroRepo.save(adv1);

            System.out.println("✅ Banco de dados populado com dados de teste!");
        }
    }
}