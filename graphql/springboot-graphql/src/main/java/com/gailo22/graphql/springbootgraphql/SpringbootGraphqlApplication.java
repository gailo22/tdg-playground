package com.gailo22.graphql.springbootgraphql;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

@SpringBootApplication
public class SpringbootGraphqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootGraphqlApplication.class, args);
	}

//	@Bean
//	CommandLineRunner init() {
//		return args -> {
//			String schema = "type Query{hello: String}";
//
//			SchemaParser schemaParser = new SchemaParser();
//			TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);
//
//			RuntimeWiring runtimeWiring = newRuntimeWiring()
//					.type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
//					.build();
//
//			SchemaGenerator schemaGenerator = new SchemaGenerator();
//			GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
//
//			GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
//			ExecutionResult executionResult = build.execute("{hello}");
//
//			System.out.println(executionResult.getData().toString());
//		};
//	};

}
