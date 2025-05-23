/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.menu;

/**
 *
 * @author Daniel freitas
 */


import javax.swing.*;
import java.sql.*;

public class Menu {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sistema de Cadastro");
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // ======= CAMPOS DE PESQUISA =======
        JLabel labelPesquisar = new JLabel("Pesquisar por nome:");
        labelPesquisar.setBounds(20, 20, 150, 25);
        frame.add(labelPesquisar);

        JTextField pesquisarField = new JTextField();
        pesquisarField.setBounds(170, 20, 200, 25);
        frame.add(pesquisarField);

        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBounds(380, 20, 90, 25);
        frame.add(btnPesquisar);

        // ======= CAMPOS DO FORMULÁRIO =======
        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(20, 60, 80, 25);
        frame.add(labelNome);

        JTextField nomeField = new JTextField();
        nomeField.setBounds(100, 60, 370, 25);
        frame.add(nomeField);

        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setBounds(20, 100, 80, 25);
        frame.add(labelEmail);

        JTextField emailField = new JTextField();
        emailField.setBounds(100, 100, 370, 25);
        frame.add(emailField);

        JLabel labelCpf = new JLabel("CPF:");
        labelCpf.setBounds(20, 140, 80, 25);
        frame.add(labelCpf);

        JTextField cpfField = new JTextField();
        cpfField.setBounds(100, 140, 370, 25);
        frame.add(cpfField);

        JLabel labelTelefone = new JLabel("Telefone:");
        labelTelefone.setBounds(20, 180, 80, 25);
        frame.add(labelTelefone);

        JTextField telefoneField = new JTextField();
        telefoneField.setBounds(100, 180, 370, 25);
        frame.add(telefoneField);

        JLabel labelEndereco = new JLabel("Endereço:");
        labelEndereco.setBounds(20, 220, 80, 25);
        frame.add(labelEndereco);

        JTextField enderecoField = new JTextField();
        enderecoField.setBounds(100, 220, 370, 25);
        frame.add(enderecoField);

        JLabel labelGenero = new JLabel("Gênero (M/F):");
        labelGenero.setBounds(20, 260, 150, 25);
        frame.add(labelGenero);

        JTextField generoField = new JTextField();
        generoField.setBounds(170, 260, 300, 25);
        frame.add(generoField);

        JLabel labelData = new JLabel("Data Nascimento(YYYY-MM-DD):");
        labelData.setBounds(20, 300, 180, 25);
        frame.add(labelData);

        JTextField dataField = new JTextField();
        dataField.setBounds(210, 300, 260, 25);
        frame.add(dataField);

        // ======= BOTÕES =======
        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBounds(80, 340, 120, 30);
        frame.add(btnAdicionar);

        JButton btnDeletar = new JButton("Deletar");
        btnDeletar.setBounds(200, 340, 120, 30);
        frame.add(btnDeletar);

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(320, 340, 120, 30);
        frame.add(btnLimpar);

        // ======= ÁREA DE RESULTADO =======
        JTextArea resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(resultadoArea);
        scrollPane.setBounds(20, 390, 450, 150);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        frame.add(scrollPane);


        // ======= AÇÕES DOS BOTÕES =======

        // PESQUISAR
        btnPesquisar.addActionListener(e -> {
            String nome = pesquisarField.getText().trim();
            if (nome.isEmpty()) {
                resultadoArea.setText("Digite um nome para pesquisar.");
                return;
            }

            try (Connection conn = getConnection()) {
                String sql = "SELECT nome, email, cpf, telefone, endereco, genero, data_nascimento FROM usuarios WHERE nome LIKE ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, "%" + nome + "%");

                ResultSet rs = stmt.executeQuery();
                StringBuilder resultado = new StringBuilder();
                while (rs.next()) {
                    resultado.append("Nome: ").append(rs.getString("nome"))
                            .append("\nEmail: ").append(rs.getString("email"))
                            .append("\nCPF: ").append(rs.getString("cpf"))
                            .append("\nTelefone: ").append(rs.getString("telefone"))
                            .append("\nEndereço: ").append(rs.getString("endereco"))
                            .append("\nGênero: ").append(rs.getString("genero"))
                            .append("\nData de Nascimento: ").append(rs.getString("data_nascimento"))
                            .append("\n--------------------------\n");
                }

                if (resultado.length() == 0) {
                    resultadoArea.setText("Usuário não encontrado.");
                } else {
                    resultadoArea.setText(resultado.toString());
                }
            } catch (Exception ex) {
                resultadoArea.setText("Erro ao buscar: " + ex.getMessage());
            }
        });

        // ADICIONAR
        btnAdicionar.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String email = emailField.getText().trim();
            String cpf = cpfField.getText().trim();
            String telefone = telefoneField.getText().trim();
            String endereco = enderecoField.getText().trim();
            String genero = generoField.getText().trim();
            String dataNascimento = dataField.getText().trim();

            // Verificações
            if (nome.isEmpty() || email.isEmpty()) {
                resultadoArea.setText("Preencha pelo menos nome e email para adicionar.");
                return;
            }
            if (nome.isEmpty() || email.isEmpty()) {
            resultadoArea.setText("Preencha pelo menos nome e email para adicionar.");
            return;
            }
            if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            resultadoArea.setText("CPF inválido. Use o formato: 000.000.000-00");
            return;
            }
            if (!endereco.contains(",")) {
            resultadoArea.setText("Endereço inválido. Use o formato: Rua X, Número, Cidade");
            return;
            }
            if (!dataNascimento.matches("\\d{4}-\\d{2}-\\d{2}")) {
            resultadoArea.setText("Data inválida. Use o formato: YYYY-MM-DD");
            return;
            }

            try (Connection conn = getConnection()) {
                String sql = "INSERT INTO usuarios (nome, email, cpf, telefone, endereco, genero, data_nascimento) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nome);
                stmt.setString(2, email);
                stmt.setString(3, cpf);
                stmt.setString(4, telefone);
                stmt.setString(5, endereco);
                stmt.setString(6, genero);
                stmt.setString(7, dataNascimento);

                stmt.executeUpdate();

                resultadoArea.setText("Usuário adicionado com sucesso!");
                nomeField.setText("");
                emailField.setText("");
                cpfField.setText("");
                telefoneField.setText("");
                enderecoField.setText("");
                generoField.setText("");
                dataField.setText("");
            } catch (Exception ex) {
                resultadoArea.setText("Erro ao adicionar: " + ex.getMessage());
            }
        });

        // DELETAR
        btnDeletar.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            if (nome.isEmpty()) {
                resultadoArea.setText("Digite o nome para deletar.");
                return;
            }

            try (Connection conn = getConnection()) {
                String sql = "DELETE FROM usuarios WHERE nome = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nome);
                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas > 0) {
                    resultadoArea.setText("Usuário deletado com sucesso!");
                } else {
                    resultadoArea.setText("Usuário não encontrado.");
                }
                nomeField.setText("");
                emailField.setText("");
            } catch (Exception ex) {
                resultadoArea.setText("Erro ao deletar: " + ex.getMessage());
            }
        });

        // LIMPAR CAMPOS
        btnLimpar.addActionListener(e -> {
            nomeField.setText("");
            emailField.setText("");
            cpfField.setText("");
            telefoneField.setText("");
            enderecoField.setText("");
            generoField.setText("");
            dataField.setText("");
            pesquisarField.setText("");
            resultadoArea.setText("");
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ======= CONEXÃO COM O BANCO =======
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/academiajava?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "1234";
        return DriverManager.getConnection(url, user, password);
    }
}

