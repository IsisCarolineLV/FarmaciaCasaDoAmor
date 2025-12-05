package model;

import java.sql.Date;
import java.sql.Time;

public class Historico {
    private int idHistorico;
    private String cpfFuncionario;
    private String nomeFuncionario; // Adicionado para facilitar a exibição
    private String acao;
    private Date dataDia; // Se o BD armazena a data separadamente
    private Time dataHora; // Se o BD armazena a hora separadamente

    // Construtor completo para o DAO
    public Historico(int idHistorico, String cpfFuncionario, String nomeFuncionario, String acao, Date dataDia, Time dataHora) {
        this.idHistorico = idHistorico;
        this.cpfFuncionario = cpfFuncionario;
        this.nomeFuncionario = nomeFuncionario;
        this.acao = acao;
        this.dataDia = dataDia;
        this.dataHora = dataHora;
    }



    public int getIdHistorico() {
        return idHistorico;
    }



    public void setIdHistorico(int idHistorico) {
        this.idHistorico = idHistorico;
    }



    public void setCpfFuncionario(String cpfFuncionario) {
        this.cpfFuncionario = cpfFuncionario;
    }



    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }



    public void setAcao(String acao) {
        this.acao = acao;
    }



    public void setDataDia(Date dataDia) {
        this.dataDia = dataDia;
    }



    public void setDataHora(Time dataHora) {
        this.dataHora = dataHora;
    }



    public String getCpfFuncionario() {
        return cpfFuncionario;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public String getAcao() {
        return acao;
    }

    public Date getDataDia() {
        return dataDia;
    }

    public Time getDataHora() {
        return dataHora;
    }
}