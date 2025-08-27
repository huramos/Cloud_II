# Requerimientos del Sistema

## Entidades
- **Users**:
  - id: NUMBER (PK)
  - name: VARCHAR2
  - email: VARCHAR2
  - role_id: NUMBER (FK)
- **Roles**:
  - id: NUMBER (PK)
  - name: VARCHAR2
  - description: VARCHAR2

## Endpoints del BFF
- POST /users: Crear usuario
- GET /users/{id}: Obtener usuario
- PUT /users/{id}: Actualizar usuario
- DELETE /users/{id}: Eliminar usuario
- POST /roles: Crear rol
- GET /roles/{id}: Obtener rol
- PUT /roles/{id}: Actualizar rol
- DELETE /roles/{id}: Eliminar rol