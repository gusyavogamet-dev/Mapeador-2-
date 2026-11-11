# Key Mapper (Android)
Mapeia teclas do teclado físico (USB/Bluetooth) para toques em coordenadas da tela.

## Como compilar
1. Abra a pasta no Android Studio e aguarde o Gradle sincronizar.
2. Run ▶ no celular (depuração USB) ou Build > Build APK.

## Como usar
1. Abra o app > "Ativar serviço" > ative "Key Mapper" em Acessibilidade.
2. Toque "Capturar tecla" e aperte uma tecla no teclado.
3. Digite X e Y (pixels) do ponto da tela a tocar e salve.
4. Em qualquer app/jogo, a tecla agora toca nesse ponto.

## Limitações
- Mouse: o Android não permite que um app comum capture cliques do mouse
  fora da própria janela. Esta versão mapeia teclado; mouse exigiria root/Shizuku.
- Toque simples (tap). Joystick/arrastar/segurar são os próximos passos.

## Gerar o APK sem PC (GitHub)
1. Crie um repositório no GitHub e envie todos os arquivos desta pasta (incluindo .github).
2. Aba Actions > "Build APK" > aguarde ~5 min.
3. Abra a execução concluída > Artifacts > baixe "KeyMapper-apk" (zip com o app-debug.apk).
4. Instale o APK no celular (permita "instalar apps desconhecidos").
