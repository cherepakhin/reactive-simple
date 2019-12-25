### Reactive в Spring Boot

[Простые операции SimpleTest.java](src/test/java/ru/perm/v/reactivesimple/SimpleTest.java)
 - создание
 - создание из массива
 - создание из потока
 - создание из последовательности
 - создание из интервала времени
 
[Слияние, комбинирование CombineTest.java](src/test/java/ru/perm/v/reactivesimple/CombineTest.java)
- merge
- zip в массив контейнеров Flux<Tuple2<String, String>>
- zip с операцией

[Выборка SelectTest.java](src/test/java/ru/perm/v/reactivesimple/SelectTest.java)

[Фильтр и преобразование FilterTest.java](src/test/java/ru/perm/v/reactivesimple/FilterTest.java)
- пропуск первых N
- пропуск по времени
- выбрать первые N
- выбрать по времени
- выбрать уникальные
- выбрать по условию (лямбда)

[Маппинг MapTest.java](src/test/java/ru/perm/v/reactivesimple/MapTest.java)
- преобразование списка **синхронно**
- преобразование списка **А**синхронно
- буферирование
- работа с буфером
- преобразование в коллекцию

[all()и any() ConditionTest.java](src/test/java/ru/perm/v/reactivesimple/ConditionTest.java)
