package com.monteiro.hexagonal_study.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;


@AnalyzeClasses(packages = "com.monteiro.hexagonal_study")
public class NamingConventionTest {

    @ArchTest
    public static final ArchRule consumer_reside_only_consumer_package = classes()
        .that()
        .haveNameMatching(".*Consumer")
        .should()
        .resideInAPackage("..adapters.in.consumer..")
        .as("Consumer classes should reside in the 'adapters.in.consumer' package");

    @ArchTest
    public static final ArchRule mapper_reside_only_mapper_package = classes()
        .that()
        .haveNameMatching(".*Mapper")
        .should()
        .resideInAnyPackage("..adapters.in.consumer.mapper", "..adapters.in.controller.mapper", "adapters.out.mapper", "adapters.out.repository.mapper")
        .as("Mapper classes should reside in the 'adapters.in.mapper' package"); 

    //Reply.... WIP    
        
    @ArchTest    
    public static final ArchRule should_be_suffixed_consumer = classes()
        .that()
        .resideInAPackage("..consumer")
        .should()
        .haveSimpleNameEndingWith("Consumer")
        .as("Classes in the 'adapters.in.consumer' package should be suffixed with 'Consumer'");

    @ArchTest    
    public static final ArchRule should_be_suffixed_mapper = classes()
        .that()
        .resideInAPackage("..mapper")
        .should()
        .haveSimpleNameEndingWith("Mapper")
        .orShould()
        .haveSimpleNameEndingWith("MapperImpl")
        .as("Classes in the 'adapters.in.mapper' package should be suffixed with 'Mapper'");
    
    //Reply.... WIP

}
