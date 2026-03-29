package br.edu.infnet.assessment.service;

import br.edu.infnet.assessment.dto.AventureiroPerfilResponseDTO;
import br.edu.infnet.assessment.exception.RecursoNaoEncontradoException;
import br.edu.infnet.assessment.model.Aventureiro;
import br.edu.infnet.assessment.dto.AventureiroRequestDTO;
import br.edu.infnet.assessment.exception.ValidacaoException;
import br.edu.infnet.assessment.model.Companheiro;
import br.edu.infnet.assessment.model.Organizacao;
import br.edu.infnet.assessment.model.Usuario;
import br.edu.infnet.assessment.repository.AventureiroRepository;
import br.edu.infnet.assessment.repository.OrganizacaoRepository;
import br.edu.infnet.assessment.repository.ParticipacaoMissaoRepository;
import br.edu.infnet.assessment.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AventureiroService {

    private final AventureiroRepository aventureiroRepository;
    private final OrganizacaoRepository organizacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ParticipacaoMissaoRepository participacaoMissaoRepository;

    // O Spring injeta o repository automaticamente aqui
    public AventureiroService(AventureiroRepository aventureiroRepository,
                              OrganizacaoRepository organizacaoRepository,
                              UsuarioRepository usuarioRepository, ParticipacaoMissaoRepository participacaoMissaoRepository) {
        this.aventureiroRepository = aventureiroRepository;
        this.organizacaoRepository = organizacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.participacaoMissaoRepository = participacaoMissaoRepository;
    }

    public List<Aventureiro> getAll() {
        return aventureiroRepository.findAll();
    }

    @Transactional
    public Aventureiro registrar(AventureiroRequestDTO dto) {

        // Valida as regras básicas (nome, classe, nível > 0)
        validarRegistro(dto);

        // Busca a Organização e o Usuário
        Organizacao org = organizacaoRepository.findById(dto.getOrganizacaoId())
                .orElseThrow(() -> new RuntimeException("Organização não encontrada"));

        Usuario usr = usuarioRepository.findById(dto.getUsuarioResponsavelId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Aventureiro novoAventureiro = new Aventureiro();
        novoAventureiro.setOrganizacao(org);
        novoAventureiro.setUsuarioResponsavel(usr);
        novoAventureiro.setNome(dto.getNome().trim());
        novoAventureiro.setClasse(dto.getClasse());
        novoAventureiro.setNivel(dto.getNivel());

        // Regras estritas da criação:
        novoAventureiro.setAtivo(true);
        novoAventureiro.setCompanheiro(null);

        // Salva no PostgreSQL
        return aventureiroRepository.save(novoAventureiro);
    }

    private void validarRegistro(AventureiroRequestDTO dto) {
        List<String> erros = new ArrayList<>();

        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            erros.add("O nome do aventureiro é obrigatório e não pode ser vazio");
        }

        if (dto.getClasse() == null) {
            erros.add("A classe deve pertencer ao conjunto permitido");
        }

        if (dto.getNivel() == null || dto.getNivel() < 1) {
            erros.add("O nivel deve ser maior ou igual a 1");
        }

        if (!erros.isEmpty()) {
            throw new ValidacaoException(erros);
        }
    }

    public Aventureiro buscarPorId(Long id) {
        return aventureiroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aventureiro não encontrado na Guilda."));
    }

    @Transactional
    public Aventureiro atualizar(Long id, AventureiroRequestDTO dto) {

        Aventureiro aventureiroExistente = buscarPorId(id);

        validarRegistro(dto);

        aventureiroExistente.setNome(dto.getNome().trim());
        aventureiroExistente.setClasse(dto.getClasse());
        aventureiroExistente.setNivel(dto.getNivel());

        return aventureiroRepository.save(aventureiroExistente);
    }

    // 5️⃣ Encerrar vínculo com a guilda
    @Transactional
    public Aventureiro inativar(Long id) {
        Aventureiro aventureiro = buscarPorId(id);
        aventureiro.setAtivo(false);
        return aventureiroRepository.save(aventureiro); // <-- Adicionado o save
    }

    // 6️⃣ Recrutar novamente
    @Transactional
    public Aventureiro recrutar(Long id) {
        Aventureiro aventureiro = buscarPorId(id);
        aventureiro.setAtivo(true);
        return aventureiroRepository.save(aventureiro); // <-- Adicionado o save
    }

    private void validarCompanheiro(br.edu.infnet.assessment.dto.CompanheiroRequestDTO dto) {
        java.util.List<String> erros = new java.util.ArrayList<>();

        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            erros.add("O nome do companheiro é obrigatório");
        }
        if (dto.getEspecie() == null) {
            erros.add("A espécie do companheiro deve pertencer ao conjunto permitido");
        }
        if (dto.getLealdade() == null || dto.getLealdade() < 0 || dto.getLealdade() > 100) {
            erros.add("A lealdade deve estar entre 0 e 100");
        }

        if (!erros.isEmpty()) {
            throw new br.edu.infnet.assessment.exception.ValidacaoException(erros);
        }
    }

    // 7️⃣ Definir ou substituir companheiro
    @Transactional
    public Aventureiro salvarCompanheiro(Long idAventureiro, br.edu.infnet.assessment.dto.CompanheiroRequestDTO dto) {

        Aventureiro aventureiro = buscarPorId(idAventureiro);

        validarCompanheiro(dto);

        Companheiro companheiro = new Companheiro();
        companheiro.setNome(dto.getNome().trim());
        companheiro.setEspecie(dto.getEspecie());
        companheiro.setLealdade(dto.getLealdade());
        companheiro.setAventureiro(aventureiro);

        aventureiro.setCompanheiro(companheiro);
        return aventureiroRepository.save(aventureiro);
    }

    @Transactional
    public void removerCompanheiro(Long idAventureiro) {
        Aventureiro aventureiro = buscarPorId(idAventureiro);
        aventureiro.setCompanheiro(null);
        aventureiroRepository.save(aventureiro);
    }

    public Page<Aventureiro> buscarPorNome(String nome, Pageable pageable) {
        return aventureiroRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public AventureiroPerfilResponseDTO obterPerfilCompleto(Long id) {
        Aventureiro a = buscarPorId(id);

        Long totalParticipacoes = participacaoMissaoRepository.countByAventureiroId(id);

        String ultimaMissao = participacaoMissaoRepository.findTopByAventureiroIdOrderByDataRegistroDesc(id)
                .map(p -> p.getMissao().getTitulo())
                .orElse("Nenhuma missão registrada");

        AventureiroPerfilResponseDTO perfil = new AventureiroPerfilResponseDTO();
        perfil.setId(a.getId());
        perfil.setNome(a.getNome());
        perfil.setClasse(a.getClasse().name());
        perfil.setNivel(a.getNivel());
        perfil.setAtivo(a.getAtivo());
        perfil.setTotalParticipacoes(totalParticipacoes);
        perfil.setTituloUltimaMissao(ultimaMissao);

        if (a.getCompanheiro() != null) {
            perfil.setNomeCompanheiro(a.getCompanheiro().getNome());
            perfil.setEspecieCompanheiro(a.getCompanheiro().getEspecie().name());
        }

        return perfil;
    }




}