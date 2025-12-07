document.addEventListener("DOMContentLoaded", function() {
    // Configurar eventos nos botões ao carregar a página
    const btnFluxo = document.getElementById("btn-fluxo-caixa");
    if (btnFluxo) {
        btnFluxo.addEventListener("click", gerarRelatorioFluxoCaixa);
    }

    const btnCategoria = document.getElementById("btn-categoria");
    if (btnCategoria) {
        btnCategoria.addEventListener("click", gerarRelatorioCategoria);
    }

    const btnPosJogo = document.getElementById("btn-pos-jogo");
    if (btnPosJogo) {
        btnPosJogo.addEventListener("click", gerarRelatorioPosJogo);
    }
});

/**
 * Função auxiliar para realizar o download do arquivo
 * O backend retorna um Content-Disposition: attachment, então window.location.href
 * inicia o download sem sair da página.
 */
function baixarArquivo(url) {
    // Verifica se os parâmetros estão preenchidos
    if (url.includes("=undefined") || url.includes("=null") || url.includes("=")) {
       // Validação básica para evitar chamadas com campos vazios, se necessário
    }

    console.log("Baixando relatório de: " + url);
    window.location.href = url;
}

function gerarRelatorioFluxoCaixa() {
    const mes = document.getElementById("select-mes").value;
    const ano = document.getElementById("select-ano").value;

    if (!mes || !ano) {
        alert("Por favor, selecione o Mês e o Ano.");
        return;
    }

    // Monta a URL conforme definido no RelatoriosController
    const url = `/api/relatorios/fluxo-caixa?mes=${mes}&ano=${ano}`;
    baixarArquivo(url);
}

function gerarRelatorioCategoria() {
    const categoria = document.getElementById("select-categoria").value;

    if (!categoria) {
        alert("Por favor, selecione uma Categoria.");
        return;
    }

    // Monta a URL conforme definido no RelatoriosController
    const url = `/api/relatorios/categoria?nome=${encodeURIComponent(categoria)}`;
    baixarArquivo(url);
}

function gerarRelatorioPosJogo() {
    const jogo = document.getElementById("game-select").value;

    if (!jogo) {
        alert("Por favor, selecione um Jogo.");
        return;
    }

    // Monta a URL conforme definido no RelatoriosController
    const url = `/api/relatorios/pos-jogo?jogo=${encodeURIComponent(jogo)}`;
    baixarArquivo(url);
}