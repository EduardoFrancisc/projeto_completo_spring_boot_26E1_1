package br.edu.infnet.assessment.config;

import br.edu.infnet.assessment.model.*;
import br.edu.infnet.assessment.enums.*;
import br.edu.infnet.assessment.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AventuraDataSeeder implements CommandLineRunner {

    private final OrganizacaoRepository organizacaoRepo;
    private final UsuarioRepository usuarioRepo;
    private final AventureiroRepository aventureiroRepo;
    private final MissaoRepository missaoRepo;

    public AventuraDataSeeder(OrganizacaoRepository organizacaoRepo,
                              UsuarioRepository usuarioRepo,
                              AventureiroRepository aventureiroRepo,
                              MissaoRepository missaoRepo) {
        this.organizacaoRepo = organizacaoRepo;
        this.usuarioRepo = usuarioRepo;
        this.aventureiroRepo = aventureiroRepo;
        this.missaoRepo = missaoRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verifica se o schema aventura já tem dados. Se estiver vazio, ele popula.
        if (aventureiroRepo.count() == 0) {

            // 1. Pega a PRIMEIRA Organização e o PRIMEIRO Usuário que o outro time já cadastrou no schema audit
            Organizacao orgExistente = organizacaoRepo.findAll().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("Nenhuma organização encontrada no schema audit!"));

            Usuario usuarioExistente = usuarioRepo.findAll().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("Nenhum usuário encontrado no schema audit!"));

            System.out.println("🔄 Semeando o schema 'aventura' usando a Organização: " + orgExistente.getNome());

            // 2. Cria o Aventureiro 1 (Com Companheiro)
            Aventureiro adv1 = new Aventureiro();
            adv1.setOrganizacao(orgExistente);
            adv1.setUsuarioResponsavel(usuarioExistente);
            adv1.setNome("Aragorn Legado");
            adv1.setClasse(ClasseAventureiro.GUERREIRO);
            adv1.setNivel(15);
            adv1.setAtivo(true);

            Companheiro comp1 = new Companheiro();
            comp1.setNome("Lobo Gigante");
            comp1.setEspecie(EspecieCompanheiro.LOBO);
            comp1.setLealdade(95);
            comp1.setAventureiro(adv1); // Ligação bidirecional
            adv1.setCompanheiro(comp1); // Ligação bidirecional

            aventureiroRepo.save(adv1);

            // 3. Cria o Aventureiro 2 (Sem companheiro)
            Aventureiro adv2 = new Aventureiro();
            adv2.setOrganizacao(orgExistente);
            adv2.setUsuarioResponsavel(usuarioExistente);
            adv2.setNome("Gandalf Teste");
            adv2.setClasse(ClasseAventureiro.MAGO);
            adv2.setNivel(20);
            adv2.setAtivo(true);

            aventureiroRepo.save(adv2);

            // 4. Cria uma Missão para essa mesma Organização
            Missao missao = new Missao();
            missao.setOrganizacao(orgExistente);
            missao.setTitulo("Limpar a Caverna dos Goblins");
            missao.setNivelPerigo(NivelPerigo.MEDIO);
            missao.setStatus(StatusMissao.PLANEJADA);

            missaoRepo.save(missao);

            System.out.println("✅ Schema 'aventura' populado com sucesso!");
        }
    }
}