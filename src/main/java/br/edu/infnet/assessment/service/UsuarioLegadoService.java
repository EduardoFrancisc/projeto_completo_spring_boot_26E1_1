package br.edu.infnet.assessment.service;

import br.edu.infnet.assessment.model.Organizacao;
import br.edu.infnet.assessment.model.Role;
import br.edu.infnet.assessment.model.Usuario;
import br.edu.infnet.assessment.repository.OrganizacaoRepository;
import br.edu.infnet.assessment.repository.RoleRepository;
import br.edu.infnet.assessment.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class UsuarioLegadoService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final OrganizacaoRepository organizacaoRepository;

    public UsuarioLegadoService(UsuarioRepository usuarioRepository, RoleRepository roleRepository, OrganizacaoRepository organizacaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.organizacaoRepository = organizacaoRepository;
    }

    // 1. Listar usuários com suas roles
    public List<Usuario> listarUsuariosComSuasRoles() {
        return usuarioRepository.findAllComRoles();
    }

    // 2. Listar roles com suas permissions
    public List<Role> listarRolesComSuasPermissoes() {
        return roleRepository.findAllComPermissions();
    }

    // 3. Persistir um novo usuário associado a uma organização existente
    @Transactional
    public Usuario criarUsuarioNaOrganizacao(Long idOrganizacao, Usuario novoUsuario) {
        // Busca a organização existente (se não achar, lança erro genérico, você pode tratar melhor depois)
        Organizacao org = organizacaoRepository.findById(idOrganizacao)
                .orElseThrow(() -> new RuntimeException("Organização não encontrada"));

        novoUsuario.setOrganizacao(org);

        novoUsuario.setCreatedAt(OffsetDateTime.now());
        novoUsuario.setUpdatedAt(OffsetDateTime.now());

        return usuarioRepository.save(novoUsuario);
    }
}