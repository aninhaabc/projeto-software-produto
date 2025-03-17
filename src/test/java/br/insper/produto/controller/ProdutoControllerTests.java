package br.insper.produto.controller;

import br.insper.produto.produto.Produto;
import br.insper.produto.produto.ProdutoService;
import br.insper.produto.produto.ProdutoController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoControllerTests {

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private ProdutoController produtoController;

    @Test
    void testCriarProduto() {
        Produto produto = new Produto();
        produto.setId("1");
        produto.setNome("Produto Teste");

        when(produtoService.criarProduto(produto)).thenReturn(produto);

        ResponseEntity<Produto> resposta = produtoController.criarProduto(produto);

        assertEquals(OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertEquals("Produto Teste", resposta.getBody().getNome());
        verify(produtoService, times(1)).criarProduto(produto);
    }

    @Test
    void testBuscarPorId_Encontrado() {
        Produto produto = new Produto();
        produto.setId("1");
        produto.setNome("Produto Teste");

        when(produtoService.buscarPorId("1")).thenReturn(Optional.of(produto));

        ResponseEntity<Produto> resposta = produtoController.buscarPorId("1");

        assertEquals(OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertEquals("Produto Teste", resposta.getBody().getNome());
    }

    @Test
    void testBuscarPorId_NaoEncontrado() {
        when(produtoService.buscarPorId("2")).thenReturn(Optional.empty());

        ResponseEntity<Produto> resposta = produtoController.buscarPorId("2");

        assertEquals(NOT_FOUND, resposta.getStatusCode());
        assertNull(resposta.getBody());
    }

    @Test
    void testListarTodos() {
        Produto produto = new Produto();
        produto.setId("1");
        produto.setNome("Produto Teste");

        List<Produto> produtos = Arrays.asList(produto);
        when(produtoService.listarTodos()).thenReturn(produtos);

        ResponseEntity<List<Produto>> resposta = produtoController.listarTodos();

        assertEquals(OK, resposta.getStatusCode());
        assertEquals(1, resposta.getBody().size());
        assertEquals("Produto Teste", resposta.getBody().get(0).getNome());
    }

    @Test
    void testDecrementarEstoque_Sucesso() {
        when(produtoService.decrementarEstoque("1", 5)).thenReturn(true);

        ResponseEntity<String> resposta = produtoController.decrementarEstoque("1", 5);

        assertEquals(OK, resposta.getStatusCode());
        assertEquals("Estoque atualizado com sucesso.", resposta.getBody());
    }

    @Test
    void testDecrementarEstoque_Falha() {
        when(produtoService.decrementarEstoque("1", 15)).thenReturn(false);

        ResponseEntity<String> resposta = produtoController.decrementarEstoque("1", 15);

        assertEquals(BAD_REQUEST, resposta.getStatusCode());
        assertEquals("Estoque insuficiente ou produto não encontrado.", resposta.getBody());
    }
}
