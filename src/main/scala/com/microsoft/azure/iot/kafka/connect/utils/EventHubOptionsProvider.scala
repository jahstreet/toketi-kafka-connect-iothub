package com.microsoft.azure.iot.kafka.connect.utils

import com.epam.honw.{AzureKeyVaultAccessEnvVariableBasedProvider, AzureKeyVaultKeyProvider}

class EventHubOptionsProvider(val azureKeyVaultUrl: String) {

  private lazy val credentialsProvider =
    new AzureKeyVaultKeyProvider(azureKeyVaultUrl, new AzureKeyVaultAccessEnvVariableBasedProvider())

  def getCredential(keyVaultSecretName: String): String = {
    val credentialEntry = credentialsProvider.getCredentialEntry(keyVaultSecretName).getCredential
    String.valueOf(credentialEntry)
  }

}
