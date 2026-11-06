apksigner sign \
  --ks ~/.android/debug.keystore \
  --ks-pass pass:android \
  --min-sdk-version 29 \
  --v1-signing-enabled true \
  --v2-signing-enabled true \
  --v3-signing-enabled true \
  --out PowerSwitch.apk \
  app/build/outputs/apk/release/app-release-unsigned.apk
