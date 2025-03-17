package br.insper.produto.service;

import br.insper.produto.produto.Produto;
import br.insper.produto.produto.ProdutoRepository;
import br.insper.produto.produto.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTests {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId("1");
        produto.setNome("Produto Teste");
        produto.setQuantidadeEmEstoque(10);
    }

    @Test
    void testCriarProduto() {
        when(produtoRepository.save(produto)).thenReturn(produto);
        Produto resultado = produtoService.criarProduto(produto);
        assertNotNull(resultado);
        assertEquals("Produto Teste", resultado.getNome());
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    void testBuscarPorId_Encontrado() {
        when(produtoRepository.findById("1")).thenReturn(Optional.of(produto));
        Optional<Produto> resultado = produtoService.buscarPorId("1");
        assertTrue(resultado.isPresent());
        assertEquals("Produto Teste", resultado.get().getNome());
    }

    @Test
    void testBuscarPorId_NaoEncontrado() {
        when(produtoRepository.findById("2")).thenReturn(Optional.empty());
        Optional<Produto> resultado = produtoService.buscarPorId("2");
        assertFalse(resultado.isPresent());
    }

    @Test
    void testListarTodos() {
        List<Produto> produtos = Arrays.asList(produto);
        when(produtoRepository.findAll()).thenReturn(produtos);
        List<Produto> resultado = produtoService.listarTodos();
        assertEquals(1, resultado.size());
        assertEquals("Produto Teste", resultado.get(0).getNome());
    }

    @Test
    void testDecrementarEstoque_Sucesso() {
        when(produtoRepository.findById("1")).thenReturn(Optional.of(produto));
        when(produtoRepository.save(produto)).thenReturn(produto);
        boolean resultado = produtoService.decrementarEstoque("1", 5);
        assertTrue(resultado);
        assertEquals(5, produto.getQuantidadeEmEstoque());
    }

    @Test
    void testDecrementarEstoque_EstoqueInsuficiente() {
        when(produtoRepository.findById("1")).thenReturn(Optional.of(produto));
        boolean resultado = produtoService.decrementarEstoque("1", 15);
        assertFalse(resultado);
    }

    @Test
    void testDecrementarEstoque_ProdutoNaoEncontrado() {
        when(produtoRepository.findById("2")).thenReturn(Optional.empty());
        boolean resultado = produtoService.decrementarEstoque("2", 5);
        assertFalse(resultado);
    }
}

