package ru.sfedu.Arch.providers;

import java.util.List;
import java.util.Optional;

public interface IMetadataProvider {
    Optional<List> getAllSchemas();
    Optional<List> getAllTables();
    Optional<List> getAllPrivileges();
    Optional<List> getAllDomains();
}
