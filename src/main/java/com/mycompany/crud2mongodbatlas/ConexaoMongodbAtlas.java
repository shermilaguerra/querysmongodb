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
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;




public class ConexaoMongodbAtlas {

 public static void main( String[] args ) {
        // Substitua o espaço reservado pela seção de conexão da sua implantação do MongoDB
        
        String uri = "mongodb+srv://milastoreadm_db_user:YhOcAwYEmY3t1HNr@clusterjava2.zodgfal.mongodb.net/";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            //Base de dados se chama: "matriculas"
            MongoDatabase database = mongoClient.getDatabase("matriculas");
            //Nome da tabela : "estudantes"
            MongoCollection<Document> collection = database.getCollection("estudantes");                     
            System.out.print("");
            
            System.out.println("-------------------A primeira busca no mongodb con find----------------- ");
            Document doc = collection.find(eq("nome", "Ryan")).first();
            // se existir esse campo converte esse doc pra json e imprime
            if (doc != null) {
                System.out.println("");
                System.out.println(doc.toJson());
                System.out.println("");
            } else {
                System.out.println("Não encontrou nenhum documento.");
            }
            //mongoClient.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
          
      
             System.out.println("-------------------------final da conexao-------------- ");
        }
    }
}
