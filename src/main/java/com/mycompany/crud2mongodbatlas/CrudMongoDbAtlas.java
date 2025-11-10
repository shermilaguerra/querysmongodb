/*
 * esse codigo funciona em:"JProduct Version: Apache NetBeans IDE 25
Java: 21.0.6; OpenJDK 64-Bit Server VM 21.0.6+2
Runtime: OpenJDK Runtime Environment 21.0.6+2
System: Linux version 5.0.0-23-generic running on amd64; UTF-8; pt_BR (nb)
 * Autor: Shermila Guerra Santa Cruz
 */
package com.mycompany.crud2mongodbatlas;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class CrudMongoDbAtlas {
    
    private MongoCollection<Document> collection;
    
    public CrudMongoDbAtlas() {
        // Construtor vazio
    }
    
    public void inicializarConexao() {
        //"mongodb+srv://milastoreadm_db_user:YhOcAwYEmY3t1HNr@clusterjava2.zodgfal.mongodb.net/?retryWrites=true&w=majority";
                     
        String uri = "mongodb+srv://milastoreadm_db_user:YhOcAwYEmY3t1HNr@clusterjava2.zodgfal.mongodb.net/?retryWrites=true&w=majority";
        
        try {
            MongoClient mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("matriculas");
            this.collection = database.getCollection("estudantes");
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro na conexão: " + e.getMessage());
  
        }
    }
    
    public boolean deletaUmAluno(String nome) {
        try {
            System.out.println("------------------- Deletando aluno no MongoDB ----------------- ");
            DeleteResult deleteResult = collection.deleteOne(eq("Nome", nome));
            long deletedCount = deleteResult.getDeletedCount();
            
            System.out.println("Deleted " + deletedCount + " document(s).");
            
            if (deletedCount > 0) {
                System.out.println("Aluno deletado satisfatoriamente!");
                return true;
            } else {
                System.out.println("Nenhum aluno encontrado com o nome: " + nome);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Erro ao deletar aluno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean insereUmAluno(String nome, int idade, String cidade) {
        try {
            System.out.println("------------------- Inserindo aluno no MongoDB ----------------- ");
            
            Document document = new Document("Nome", nome)               
                    .append("Idade", idade)
                    .append("Cidade", cidade);
            
            InsertOneResult result = collection.insertOne(document);
            System.out.println("Documento inserido satisfatoriamente!");
            return result.wasAcknowledged();
            
        } catch (Exception e) {
            System.err.println("Erro ao inserir aluno: " + e.getMessage());
            
            return false;
        }
    }
    
    public void buscaAlunoPorNome(String nome) {
        try {
            System.out.println("------------------- Buscando aluno no MongoDB ----------------- ");
            Document doc = collection.find(eq("Nome", nome)).first();
            
            if (doc != null) {
                System.out.println("\nAluno encontrado:");
                System.out.println("Nome: " + doc.getString("Nome"));
                System.out.println("Idade: " + doc.getInteger("Idade"));
                System.out.println("Cidade: " + doc.getString("Cidade"));
                System.out.println("JSON: " + doc.toJson() + "\n");
            } else {
                System.out.println("Não encontrou nenhum aluno com o nome: " + nome);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar aluno: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void listaTodosAlunos() {
        try {
            System.out.println("------------------- Listando todos os alunos ----------------- ");
            long count = collection.countDocuments();
            System.out.println("Total de alunos: " + count);
            
            if (count > 0) {
                System.out.println("\nLista de alunos:");
                for (Document doc : collection.find()) {
                    System.out.println("- Nome: " + doc.getString("Nome") + 
                                     ", Idade: " + doc.getInteger("Idade") + 
                                     ", Cidade: " + doc.getString("Cidade"));
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CrudMongoDbAtlas app = new CrudMongoDbAtlas();
        app.inicializarConexao();
        
        if (app.collection != null) {
            System.out.println("=== TESTANDO OPERAÇÕES CRUD ===");
            
            // Listar alunos existentes
            app.listaTodosAlunos();
            
            // Inserir um novo aluno
            System.out.println("\n>>> Inserindo novo aluno...");
            boolean inserido = app.insereUmAluno("Martha Prado", 29, "São Paulo");
            
            if (inserido) {
                // Buscar o aluno inserido
                System.out.println("\n>>>Procurando aluno inserido...");
                app.buscaAlunoPorNome("Martha Prado");
                
                // Deletar o aluno
                System.out.println("\n>>> Deletando aluno...");
                app.deletaUmAluno("Martha Prado");
            }
            
            // Tentativa de deletar aluno que não existe
            //System.out.println("\n>>> Tentando deletar aluno inexistente...");
            //app.deletaUmAluno("AlunoInexistente");
        }
        
        System.out.println("------------------------- Final da execução ---------------- ");
    }
}