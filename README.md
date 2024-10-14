
# 🌍 Altour: Turismo Sostenible en Tiempo Real

Altour es una innovadora **web app** diseñada para mitigar el impacto del turismo masivo en ciudades como **Barcelona**, **Madrid** y **Valencia**. A través de un **mapa de calor en tiempo real** y **algoritmos de recomendación inteligentes**, Altour permite a los turistas evitar las áreas más congestionadas, ofreciéndoles destinos alternativos personalizados para una experiencia turística más tranquila y sostenible.

## 🚨 Problema que Soluciona

Ciudades como **Barcelona**, **Madrid** y **Valencia** enfrentan un grave problema de **turismo masivo**, lo que genera:

- Saturación en áreas clave.
- Desplazamiento de comercios locales.
- Aumento del costo de vida.
- Deterioro de infraestructuras públicas.

Este fenómeno afecta tanto a los **residentes** como a los **turistas**, provocando una experiencia negativa en ambas partes. **Altour** busca redistribuir de manera más eficiente el flujo turístico para aliviar la presión sobre zonas congestionadas.

## 💡 Nuestra Solución

**Altour** es una **web app mobile-first** que utiliza:

- **Mapas de calor en tiempo real**: Identifica y muestra las áreas más congestionadas en tiempo real.
- **Recomendaciones personalizadas**: Sugiere destinos alternativos menos concurridos basados en la ubicación, intereses y congestión actual.
- **Filtros inteligentes**: Permite a los usuarios seleccionar el tipo de actividades, distancias y niveles de saturación para encontrar destinos personalizados.

Los datos provienen de APIs gratuitas como **HERE Maps** y **OpenStreetMap**, lo que garantiza una información precisa y actualizada sobre el tráfico y la movilidad en las ciudades.

## ✨ Características Clave

- **🚪 Autenticación de usuarios**: Registro e inicio de sesión seguros.
- **📊 Mapa de congestión en tiempo real**: Datos actualizados sobre la saturación turística.
- **🗺️ Recomendaciones alternativas**: Sugerencias de destinos en función de la ubicación actual y preferencias del usuario.
- **🔍 Filtros personalizados**: Búsqueda basada en actividades, distancia y nivel de saturación.
- **ℹ️ Información detallada**: Datos completos de los puntos de interés recomendados (horarios, precios, opiniones).

## ⚙️ Tecnologías Utilizadas

### Frontend:
- **React**  **Next.js**  **Tailwind CSS**  **TypeScript**

### Backend:
- **Java 17** - **Spring 3** - **PostgreSQL** - **Reddis** - **Spring Security con autenticación JWT**

### Documentacion swagger:
-  /api/v1/docs/swagger-ui/index.html

## 🚀 Instalación y Configuración

### 1. Para Backend, clonar repositorio:

```bash
git clone https://github.com/igrowker/i003-altour-back.git
```

### 2. Instalar dependencias desde archivo pom
```bash
pom.xml
```

### 3. Crear bases de datos para reddis y postrgreSQL. Setear credenciales en application.properties:

```bash
spring.data.redis.password=${REDIS_PASSWORD}
spring.datasource.url=jdbc:postgresql://${DATASOURCE_ALTOUR}
spring.datasource.username=${USERNAME_POSTGRES}
spring.datasource.password=${PASSWORD_POSTGRES}
```
### 4. Crear cuentas, obtener API Keys y setear valores en application.properties:

```bash
here_maps.api.key=${API_KEY_HERE_MAPS}
best_time.api.key=${API_PRIV_KEY_BEST_TIME}
best_time.api.pub.key=${API_PUB_KEY_HERE_MAPS}
```

### 4. Iniciar la aplicación: Ejecutar archivo AltourApplication

```bash
src/main/java/com/igrowker/altour/AltourApplication.java
```

### 5. Para frontend, seguir Readme en:

```bash
git clone https://github.com/igrowker/i003-altour-front.git
```

## 🛠️ Cómo Contribuir

¡Contribuciones son bienvenidas! Si deseas colaborar con este proyecto, sigue estos pasos:

1. **Fork** el repositorio.
2. Crea una nueva rama con una mejora o corrección.
3. Haz un **pull request** cuando tu cambio esté listo.

## 👥 Equipo

### Project Manager:
- **[Christian Gil](https://www.linkedin.com/in/christiangil72/)**

### Frontend:
- **[Yolanda Lopez Vidal](https://www.linkedin.com/in/yolovi/)**
- **[Melody Callejas Nuñez](https://www.linkedin.com/in/melodycallejas)**
- **[Jonathan Muñoz](https://www.linkedin.com/in/jomuarribas/)**
- **[Sebastian Barrientos](https://www.linkedin.com/in/sebasbarrientos/)** 

### Backend:
- **[David Costa](https://www.linkedin.com/in/david-costa-yafar)**
- **[Pablo Alonso Menendez](https://www.linkedin.com/in/pablo-alonsom/)**
- **[Nicolas Irigoyen](https://www.linkedin.com/in/nirigoyen/)**
- **[Luis Fernando García Barrero](https://www.linkedin.com/in/l-fernando-garcía-barrero/)** 

### QA:
- **[Ana María Delgado Rodriguez](https://www.linkedin.com/in/anamariadelgador/)**

### UX/UI:
- **[Ramón Montosa](https://www.linkedin.com/in/ramon-montosa-palma/)**
- **[Matías Villanueva](https://www.linkedin.com/in/matias-villanueva/)**

<br/>

---

¡Gracias por visitar **Altour**! 🌐🔍 Estamos comprometidos con la creación de un turismo más sostenible y agradable para todos.

