import React from 'react'

export default function Entry({
    nickname,
    date,
    content
}){
    //const formatDateStr = date.replace("T ", " ").replaceAll("-", ".");

    return( <div className="dark:bg-gray-100 dark:text-gray-900 mt-5 mb-2">
        <div className="container max-w-screen-2xl px-10 py-6 mx-auto rounded-lg shadow-sm dark:bg-gray-80">
            <div className="flex items-center justify-between">
                <div className="flex items-center">
                    <img src="https://source.unsplash.com/50x50/?portrait" alt="avatar" className="object-cover w-10 h-10 mx-4 rounded-full dark:bg-gray-500"/>
                    <span className="hover:underline text-xl font-semibold dark:text-gray-900">{nickname}</span>
                </div>
                <span className="text-lg font-semibold dark:text-gray-900">{date}</span>
            </div>
            <div className="mt-8">
                {content}
            </div>
        </div>
    </div>)
}