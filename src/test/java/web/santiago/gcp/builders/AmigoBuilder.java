package web.santiago.gcp.builders;

import web.santiago.gcp.dtos.AmigoDto;
import web.santiago.gcp.entities.Amigo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class AmigoBuilder {

    private Amigo amigo;
    private Collection<Amigo> amigos;
    private AmigoDto amigoDto;

    public static AmigoBuilder mockAmigoBuilder() {
        AmigoBuilder builder = new AmigoBuilder();
        builder.amigo = new Amigo(
                "Thiago",
                "M",
                "Irmão",
                "Rua Antonio Coelho Sobrinho",
                "83 9 9959-1313"
        );

        builder.amigo.setId(1L);

        return builder;
    }

    public static AmigoBuilder mockCollectionAmigosBuilder() {
        AmigoBuilder builder = new AmigoBuilder();
        builder.amigos = new ArrayList<Amigo>();

        for (int i = 0; i < 10; i++) {
            Amigo novoAmigo = new Amigo(
                    "Amigo " + i,
                    "F",
                    "Parente " + i,
                    "Rua " + i,
                    "83 9 9959-1313"
            );

            builder.amigos.add(novoAmigo);
        }

        return builder;
    }

    public static AmigoBuilder mockAmigoDtoBuilder() {
        AmigoBuilder builder = new AmigoBuilder();
        builder.amigoDto = new AmigoDto();
        builder.amigoDto.setNome("Thiago");
        builder.amigoDto.setSexo("M");
        builder.amigoDto.setParentesco("Irmão");
        builder.amigoDto.setEndereco("Rua Antonio Coelho Sobrinho");
        builder.amigoDto.setTelefone("83 9 9959-1313");

        return builder;
    }

    // Methods
    public Amigo getAmigo() {
        return this.amigo;
    }

    public AmigoDto getAmigoDto() {
        return this.amigoDto;
    }

    public Optional<Amigo> getAmigoOptional() {
        Optional<Amigo> empty = Optional.of(this.amigo);
        return empty;
    }

    public Optional<Amigo> getAmigoEmptyOptional() {
        return Optional.empty();
    }

    public Collection<Amigo> getAmigos() {
        return this.amigos;
    }
}
