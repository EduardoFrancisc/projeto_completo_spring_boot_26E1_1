package br.edu.infnet.assessment;

import br.edu.infnet.assessment.model.Organizacao;
import br.edu.infnet.assessment.model.Usuario;
import br.edu.infnet.assessment.repository.OrganizacaoRepository;
import br.edu.infnet.assessment.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
// Impede que o Spring substitua o banco Docker pelo H2 durante o teste
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioJpaTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Test
    @Transactional
    public void devePersistirUsuarioEVerificarRelacionamentos() {
        // 1. Cria e salva uma Organização
        Organizacao org = new Organizacao();
        org.setNome("Guilda Central");
        org.setAtivo(true);
        org.setCreatedAt(OffsetDateTime.now());
        org = organizacaoRepository.save(org);

        // 2. Cria e salva um Usuário atrelado à Organização
        Usuario usuario = new Usuario();
        usuario.setNome("Aventureiro Teste");
        usuario.setEmail("teste@guilda.com");
        usuario.setOrganizacao(org);
        usuario.setCreatedAt(OffsetDateTime.now());
        usuario = usuarioRepository.save(usuario);

        // 3. Busca o usuário do banco e valida os mapeamentos
        Optional<Usuario> usuarioSalvo = usuarioRepository.findById(usuario.getId());

        assertThat(usuarioSalvo).isPresent();
        assertThat(usuarioSalvo.get().getOrganizacao().getNome()).isEqualTo("Guilda Central");

        // Verifica se a lista de roles inicializou corretamente (mesmo vazia)
        assertThat(usuarioSalvo.get().getRoles()).isNotNull();
    }
}
