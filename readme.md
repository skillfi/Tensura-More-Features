# TenSura More Features

`TenSura More Features` (`tensura_mf`) — мод для Minecraft 1.21.1, який розширює [Tensura: Reincarnated] додатковими магічними блоками, рецептами, предметами та істотами. Проєкт побудований на Architectury й містить спільний код та окремі реалізації для Fabric і NeoForge.

## Можливості

- магічна енергетична мережа з генераторами, трубами та сховищами;
- магічний інкубатор і власний тип рецептів `magic_incubation`;
- магічні матеріали, блоки та інші предмети, зареєстровані модом;
- блоки-генератори на основі різних будівельних матеріалів;
- додаткові меню, клієнтські екрани та відображення магічної енергії;
- нові монстри, їхні атрибути, спавн і spawn eggs;
- data generation для рецептів, loot tables, тегів, моделей, blockstates та локалізацій;
- підтримка компонентів предметів, зокрема збереження даних спеціального спорядження під час обробки інкубатором.

## Вимоги

- JDK 21;
- Minecraft 1.21.1;
- Gradle 8.8;
- Architectury API 13.0.8;
- Tensura: Reincarnated;
- ManasCore;
- GeckoLib, SmartBrainLib і TerraBlender.

Версії залежностей для розробки вказані у [`gradle.properties`](gradle.properties). Для запуску гри також потрібен відповідний loader: Fabric або NeoForge.

## Структура проєкту

```text
common/      Спільний код і ресурси для всіх платформ
fabric/      Fabric-реалізація та Fabric entrypoints
neoforge/    NeoForge-реалізація, реєстрації та data generation
libs/        Локальні бібліотеки, що використовуються під час збірки
run/         Робочі директорії dev-середовища
```

Згенеровані ресурси спільного модуля зберігаються в `common/src/generated/resources`.

## Підготовка

1. Встановіть JDK 21 і переконайтеся, що `java -version` показує версію 21.
2. Клонуйте репозиторій і відкрийте його як Gradle-проєкт.
3. Перевірте, що локальні бібліотеки в `libs/` присутні, а залежності можуть бути завантажені.
4. Використовуйте Gradle 8.8. У цьому checkout збережено конфігурацію wrapper у `gradle/wrapper`, але скрипти `gradlew` і `gradlew.bat` не входять до репозиторію.

## Основні команди

### Перевірка й збірка

```bash
gradle common:compileJava neoforge:compileJava
gradle build
```

Для Fabric замість NeoForge можна виконати:

```bash
gradle fabric:compileJava
```

### Запуск клієнта

```bash
gradle neoforge:runClient
gradle fabric:runClient
```

У Windows використовуйте ті самі task-и через встановлений Gradle.

### Генерація даних

NeoForge data generation створює рецепти та інші ресурси у спільному generated-директорії:

```bash
gradle neoforge:runData
```

Після зміни провайдерів рецептів перевіряйте згенеровані JSON-файли в `common/src/generated/resources` і не додавайте їх до коміту, якщо вони є лише локальним результатом запуску.

## Розробка

- Платформонезалежну логіку розміщуйте в `common`.
- Код, що використовує тільки Fabric або NeoForge API, розміщуйте у відповідному платформному модулі.
- Нові реєстрації додавайте через існуючі registry-класи проєкту.
- Для рецептів використовуйте data providers NeoForge та перевіряйте унікальність їхніх ідентифікаторів.
- Після змін у Java-коді запускайте компіляцію потрібної платформи; після змін у data providers — `neoforge:runData`.

Основний namespace мода — `tensura_mf`, а головний клас спільного модуля — `com.github.skillfi.tensura_mf.TensuraMf`.

## Ліцензія

Умови використання визначені у [`LICENSE.txt`](LICENSE.txt).

[Tensura: Reincarnated]: https://www.curseforge.com/minecraft/mc-mods/tensura-reincarnated
