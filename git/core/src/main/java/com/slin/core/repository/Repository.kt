package com.slin.core.repository

/**
 * author: slin
 * date: 2020-09-07
 * description: 仓库接口
 */
interface IRepository

open class CoreRepositoryNothing() : IRepository

open class CoreRepositoryLocal<T : ILocalDataSource>(val localDataSource: T) : IRepository {

}

open class CoreRepositoryRemote<T : IRemoteDataSource>(val remoteDataSource: T) : IRepository {

}

open class CoreRepositoryBoth<R : IRemoteDataSource, L : ILocalDataSource>(
    val remoteDataSource: R,
    val localDataSource: L
) {

}
