package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exceptions.*;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private static final String MSG_SENHA_INCONPATIVEL = "Senha atual informada não coincide com a senha do usuario";
    private static final String MSG_USUARIO_EM_USO = "O usuario com id %d não pode ser deletada pois está em uso";
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private EntityManager entityManager;


    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioExistente(Long usuarioId) {
        return usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

    @Transactional
    public Usuario cadastrar(Usuario usuario) {
        entityManager.detach(usuario);
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent() && !usuarioExistente.equals(usuario)){
            throw new NegocioException(String.format("Já existe usuario cadastrado com email %S", usuario.getEmail()));
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void atualizarSenha(Long usuarioId, String senhaAntiga, String novaSenha) {
        var usuarioExistente = buscarUsuarioExistente(usuarioId);
        if (!usuarioExistente.getSenha().equals(senhaAntiga)){
            throw new SenhaIncompativelException(MSG_SENHA_INCONPATIVEL);
        }
        usuarioExistente.setSenha(novaSenha);
    }

    public void deletarUsuario(Long usuarioId) {
        try {
            usuarioRepository.deleteById(usuarioId);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, usuarioId));
        }catch (EmptyResultDataAccessException e){
            throw  new UsuarioNaoEncontradoException( usuarioId);
        }
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId) {
        var usuario = buscarUsuarioExistente(usuarioId);
        var grupo = grupoService.buscarPorId(grupoId);

        usuario.getGrupos().remove(grupo);
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId) {
        var usuario = buscarUsuarioExistente(usuarioId);
        var grupo = grupoService.buscarPorId(grupoId);

        usuario.getGrupos().add(grupo);
    }
}
