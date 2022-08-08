# mvvm-dagger_hilt-flow


#Description#

A sample project listing product data pulled over the internet using MVVM Dagger Hilt and Flow.

#Used technologies#

-MVVM
-Dagger Hilt
-Retrofit
-Coroutines
-Flow


#Network Module#

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }


    @Singleton
    @Provides
    fun provideOkHttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideApiClient(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}




#Repository#

class MainRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getPopularMovies() = flow {
        emit(NetworkResult.Loading(true))
        val response = apiService.getMostPopularMovies()
        emit(NetworkResult.Success(response))
    }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "Unknown Error"))
    }


}


#API Service#

interface ApiService {

    @GET("products")
    suspend fun getMostPopularMovies() : List<Product>
}

#ViewModel#

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
):ViewModel() {
    private var _productResponse = MutableLiveData<NetworkResult<List<Product>>>()
    val productResponse: LiveData<NetworkResult<List<Product>>> = _productResponse

    init {
        fetchAllMovies()
    }

    private fun fetchAllMovies() {
        viewModelScope.launch {
            mainRepository.getPopularMovies().collect {
                it?.let {
                    _productResponse.postValue(it)
                }
            }
        }
    }

}
![Screenshot_20220808_143519](https://user-images.githubusercontent.com/50892348/183409297-6d87b007-2772-4721-b73f-53e786773e3c.png)
![Screenshot_20220808_143536](https://user-images.githubusercontent.com/50892348/183409328-0df0c7bb-6db8-4b0a-91d9-e86af2c02d8e.png)
