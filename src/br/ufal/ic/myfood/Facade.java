package br.ufal.ic.myfood;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.core.Core;


public class Facade {

    Core core;

    public Facade() throws FileError{
        core = new Core();
    }

    public void zerarSistema(){
        core.zerarSistema();
    }

    public void encerrarSistema() throws SaveError{

        core.encerrarSistema();

    }

    public String getAtributoUsuario(String id, String atributo)
            throws UsuarioNaoExisteException,AtributoInvalido {

        return core.getAtributoUsuario(id,atributo);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws UsuarioJaExisteException, NomeInvalido, EmailInvalido, EnderecoInvalido,
            SenhaInvalida{

        core.criarUsuario(nome,email,senha,endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws CPFinvalido,UsuarioJaExisteException, NomeInvalido, EmailInvalido, EnderecoInvalido,
            SenhaInvalida {

        core.criarUsuario(nome,email,senha,endereco,cpf);
    }

    public String login(String email, String senha) throws LoginError {
       return core.login(email, senha);
    }

    public String criarEmpresa (String tipoEmpresa, String dono, String nome, String endereco, String tipoCozinha)
                            throws UsuarioNaoPodeCriarEmpresa, NomeDeEmpresaJaExiste,
                            EmpresaComMesmoNomeeLocal, NomeInvalido, EnderecoEmpresaInvalido,
                            TipoEmpresaInvalido{

        return core.createEnterprise(tipoEmpresa,dono,nome,endereco,tipoCozinha);
    }

    public String  criarEmpresa(String tipoEmpresa, String dono, String nome, String endereco,
                                String abre, String fecha, String tipoMercado)  throws UsuarioNaoPodeCriarEmpresa, NomeDeEmpresaJaExiste,
            EmpresaComMesmoNomeeLocal, FormatoDeHoraInvalido, HorarioInvalido, NomeInvalido,
            TipoEmpresaInvalido,EnderecoEmpresaInvalido,TipoMercadoInvalido {

        return core.createEnterprise(tipoEmpresa,dono,nome,endereco,abre,fecha,tipoMercado);
    }


    public String getEmpresasDoUsuario(String IdDono) throws EmpresanaoCadastrada, UsuarioNaoPodeCriarEmpresa, UsuarioNaoExisteException {
        return core.getEnterprizesOfUser(IdDono);
    }

    public String getAtributoEmpresa(String empresaId, String atributo) throws EmpresanaoCadastrada, AtributoInvalido, UsuarioNaoExisteException {
        return core.getAtributoEmpresa(empresaId, atributo);
    }

    public String getIdEmpresa(String ownerId, String nome, int indice) throws EmpresanaoCadastrada, NomeInvalido, IndiceMaiorQueEsperado, UsuarioNaoExisteException, UsuarioNaoPodeCriarEmpresa, IndiceInvalido, NaoExisteEmpresaComEsseNome {
        return core.getIdEmpresa(ownerId, nome, indice);
    }

    public void alterarFuncionamento(String mercado, String abre, String fecha)
    throws MercadoInvalido,HorarioInvalido,FormatoDeHoraInvalido{
        core.supermarketChangeOperation(mercado,abre,fecha);
    }
//# descrição: Altera o horario de funcionamento do Mercado.
//# retorno: Sem retorno

    public String criarProduto(String empresa, String nome, float valor, String categoria)
            throws NomeInvalido, ValorInvalido, CategoriaInvalido, JaExisteUmProdutoComEsseNomeParaEssaEmpresa {
        return core.createProduct(empresa, nome, valor, categoria);
    }

    public void editarProduto(String produto, String nome, float valor, String categoria)
            throws ProdutoNaoCadastrado, NomeInvalido, ValorInvalido, CategoriaInvalido {
        core.editProduct(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, String empresa, String atributo)
            throws ProdutoNaoEncontrado, AtributoNaoExiste {
        return core.getProductAtribute(nome, empresa, atributo);
    }

    public String listarProdutos(String empresa) throws EmpresaNaoEncontrada {
        return core.getProductListByEnterprise(empresa);
    }

    public String criarPedido(String cliente, String empresa)
            throws DoisPedidosMesmaEmpresa, DonoNaoPodeFazerPedido {
        return core.createOrder(cliente, empresa);
    }

    public void adicionarProduto(String numero, String produto) throws NaoExistePedidoEmAberto, AdicionarEmPedidoFechado, ProdutoNaoPertenceAEmpresa, PedidoNaoEncontrado {
        core.addProductToOrder(numero, produto);
    }

    public String getPedidos(String pedido, String atributo) throws AtributoInvalido,
            PedidoNaoEncontrado, AtributoNaoExiste,
            UsuarioNaoExisteException, EmpresanaoCadastrada {
        return core.getOrderAttribute(pedido, atributo);
    }

    public void fecharPedido(String numero) throws PedidoNaoEncontrado {
        core.closeOrder(numero);
    }

    public void removerProduto(String pedido, String produto) throws ProdutoInvalido,
            RemoverEmPedidoFechado, ProdutoNaoEncontrado, PedidoNaoEncontrado {
        core.removeProductFromOrder(pedido, produto);
    }

    public String getNumeroPedido(String cliente, String empresa, int indice)
            throws IndiceMaiorQueEsperado {
        return core.getOrderNumber(cliente, empresa, indice);
    }

}
