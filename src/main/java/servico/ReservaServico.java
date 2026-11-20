package servico;

import dominio.Carro;
import dominio.Periodo;
import dominio.Pessoa;
import dominio.Reserva;
import dominio.StatusReserva;
import excecoes.DadosInvalidosException;
import excecoes.EstadoInvalidoException;
import politica.CalculoPreco;
import politica.PrecoPadrao;
import repositorio.Repositorio;

public class ReservaServico
{
    private final Repositorio<Reserva, Integer> repositorio;

    public ReservaServico(Repositorio<Reserva, Integer> repositorio)
    {
        if (repositorio == null)
        {
            throw new DadosInvalidosException("O repositório não pode ser nulo.");
        }
        this.repositorio = repositorio;
    }

    public Reserva criarReserva(int id, Periodo periodo, Pessoa pessoa, Carro carro)
    {
        return criarReserva(id, periodo, pessoa, carro, new PrecoPadrao());
    }

    public Reserva criarReserva(int id, Periodo periodo, Pessoa pessoa, Carro carro, CalculoPreco calculoPreco)
    {
        if (periodo == null || carro == null)
        {
            throw new DadosInvalidosException("Período e carro são obrigatórios.");
        }
        if (repositorio.buscarPorId(id).isPresent())
        {
            throw new DadosInvalidosException("Já existe uma reserva cadastrada com o ID: " + id);
        }

        boolean possuiSobreposicaoAtiva = repositorio.listarTodos().stream()
                .filter(r -> r.getStatus() == StatusReserva.CRIADA)
                .filter(r -> r.getCarro().getPlaca().equalsIgnoreCase(carro.getPlaca()))
                .anyMatch(r -> r.getPeriodo().temSobreposicao(periodo));

        if (possuiSobreposicaoAtiva)
        {
            throw new EstadoInvalidoException("O veículo já possui uma reserva ativa nesse período.");
        }

        Reserva novaReserva = new Reserva(id, periodo, pessoa, carro, calculoPreco);
        repositorio.salvar(novaReserva);
        return novaReserva;
    }
}