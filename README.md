HElias - Minecraft Project Customizer
======================

[![GPL License, Version 3.0](https://img.shields.io/badge/license-GPL%203.0-blue?style=for-the-badge&logo=appveyor)](https://www.gnu.org/licenses/gpl-3.0)
![GitHub all releases](https://img.shields.io/github/downloads/HZERR/HSashokLauncherPatcher/total?color=blue&style=for-the-badge)
![GitHub commit activity](https://img.shields.io/github/commit-activity/m/HZERR/HSashokLauncherPatcher?style=for-the-badge)
![GitHub last commit](https://img.shields.io/github/last-commit/HZERR/HSashokLauncherPatcher?style=for-the-badge)
![GitHub (Pre-)Release Date](https://img.shields.io/github/release-date-pre/HZERR/HSashokLauncherPatcher?label=pre-release%20date&style=for-the-badge)
![Lines of code](https://img.shields.io/tokei/lines/github/HZERR/HSashokLauncherPatcher?style=for-the-badge)

Patcher of launchers based on the sashok724 launcher

<h2>License</h2>
If you do decide to use ANY code from the source:<b></br></br>

* You must disclose the source code of your modified work and the source code you took from this project. This means you are not allowed to use code from this project (even partially) in a closed-source (or even obfuscated) application.
* Your modified application must also be licensed under the GPL
  
</b>

<h2>В будущем</h2>
1. Поддержка модификации такого проекта как LoliLand, а также многих других</br>
2. Поддержка тем оформления для проектов, ручная кастомизация</br>

<h2>Какие проекты скорее всего не будут поддерживаться</h2>
1. StreamCraft</br>
2. Cristallix</br>
<h3>Туториал</h3>

Необходимо выбрать функции во вкладке "Проекты". На данный момент доступна поддержка 2 проектов: McSkill, MythicalWorld</br>
<h4>Внимание!</h4>
Проект McSkill нельзя модифицировать в системе Windows! Проект McSkill использует названия классов, которые недоступны в системе Windows, например: COM1, Aux, Prn</br>

Далее `необходимо изменить` настройки во вкладке "Настройки", `если они не установлены`</br>
1. Путь до проекта
2. Название проекта
3. Название лаунчера
4. Версия лаунчера
5. Сборка лаунчера
6. Путь до установленного проекта - если необходимо удалить/добавить модификацию

<b>Пример настроек:</b></br>
Путь до проекта: C:\Users\HZERROR\Desktop\MythicalWorld.jar</br>
Название проекта: HMythicalWorld.jar</br>
Название лаунчера: HZERR</br>
Версия лаунчера: 1.0</br>
Сборка лаунчера: 295, 20.04.2021</br>
Путь до установленного проекта: C:\Users\HZERR\MythicalWorld

<b>Порядок запуска процессов</b><br>

1. Очистка
2. Распаковка
3. Изменение манифеста/байткода
4. Сборка
5. Изменение ресурсов (например, изменение каких-то файлов в папке runtime)
6. Добавление/Удаление библиотеки
7. Обновление проекта

<h3>Связь</h3>

Вы можете обсудить со мной в `Telegram: @HZERROR` детали проекта

Pull requests are welcome
