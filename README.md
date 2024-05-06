### Building singed apk with github actions

#### Step 1: Add secrets to your repository

- Go to your repository
- Click on `Settings`
- Click on `Secrets`
- Click on `New repository secret`
- Add `LOCAL_PROPERTIES`, `SIGNING_KEY_ALIAS`, `SIGNING_KEY_PASSWORD` and `SIGNING_KEYSTORE_BASE64` to your repository secrets

both `SIGNING_KEYSTORE_BASE64` and `LOCAL_PROPERTIES` should be base64 encoded. To encode a file to base64, you can use the following command:
```bash
base64 keystore.jks -w 0
base64 local.properties -w 0
```

#### Step: Add signing configuration to your `build.gradle` file
```gradle
// https://developer.android.com/build/building-cmdline#groovy
    // https://developer.android.com/studio/publish/app-signing#secure-shared-keystore
    // for building with github actions
    signingConfigs {

        release {
            storeFile file("../keystore.jks")
            storePassword System.getenv("SIGNING_STORE_PASSWORD")
            keyAlias System.getenv("SIGNING_KEY_ALIAS")
            keyPassword System.getenv("SIGNING_KEY_PASSWORD")

            // Optional, specify signing versions used
            v1SigningEnabled true
            v2SigningEnabled true
        }
    }
    
    buildTypes {
        release {
            signingConfig signingConfigs.release // for github action
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
```

Reference: https://medium.com/@dcostalloyd90/automating-android-builds-with-github-actions-a-step-by-step-guide-2a02a54f59cd