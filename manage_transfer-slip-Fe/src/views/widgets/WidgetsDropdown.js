import React, { useEffect, useState } from 'react'
import {
  CRow,
  CCol,
  CCard,
  CCardBody,
} from '@coreui/react'
import '../../scss/dashboard/_dashboard.scss'
import { useHistory } from 'react-router-dom'
import axios from 'axios'

export default function WidgetsDropdown(props) {
  // history
  const history = useHistory()
  const getUser = JSON.parse(sessionStorage.user)

  //URL of api
  const URL_NUMBER_INVENTORY = "http://localhost:8080/admin/inventory/size"
  const NUMBER_PRODUCT = `http://localhost:8080/admin/products/total-product-in-inventory/${getUser.inventoryId}`;

  //useState Number
  const [inventoriesSize, setinventoriesSize] = useState("0")
  const [numberProduct, setNumberProduct] = useState("0")

  const user = JSON.parse(sessionStorage.getItem("user"))

  //setup card
  const setupWibget = [
    {
      title: 'Tổng số Chi nhánh',
      content: 'quản lý các chi nhánh cửa hàng',
      backgroundColor: 'indigo',
      number: inventoriesSize,
      color: '#ffffff',
      backgroundColorCircle: '#868686',
      colorCircle: '#ffffff',
      to: '/inventories'
    },
    {
      title: 'Tổng số sản phẩm',
      content: 'quản lý các chi nhánh cửa hàng',
      backgroundColor: '#0d6efd',
      number: numberProduct,
      color: '#ffffff',
      backgroundColorCircle: '#868686',
      colorCircle: '#ffffff',
      to: 'products/list'
    },
    {
      title: 'Tổng số Chi nhánh',
      content: 'quản lý các chi nhánh cửa hàng',
      backgroundColor: '#198754',
      number: inventoriesSize,
      color: '#ffffff',
      backgroundColorCircle: '#868686',
      colorCircle: '#ffffff',
      to: ''
    },
    {
      title: 'Tổng số Chi nhánh',
      content: 'quản lý các chi nhánh cửa hàng',
      backgroundColor: '#dc3545',
      number: inventoriesSize,
      color: '#ffffff',
      backgroundColorCircle: '#868686',
      colorCircle: '#ffffff',
      to: ''
    },

  ]

  //setUup get number
  useEffect(() => {
    axios.get(URL_NUMBER_INVENTORY,{
      headers:{
        "Authorization": user.type + " " + user.token
    }
    })
      .then(
        (res) => {
          if (res.status === 200) {
            setinventoriesSize(res.data)
          }
        }
      ).catch(

      )
  }, [])
  useEffect(() => {
    axios.get(NUMBER_PRODUCT, {
      headers: {
        'authorization': `Bearer ${getUser.token}`,
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    })
      .then(
        (res) => {
          console.log("number product: ", res.status)

          setNumberProduct(res.data)

        }
      ).catch(
        console.log
      )
  }, [])

  console.log("number product: ", numberProduct)
  function numberInventory() {
    //code here
  }


  function showWidget() {

    const displayWibget = setupWibget.map(
      (item, index) => {
        return (
          <CCol sm="6" lg="3" style={{
            margin: '0 auto'
          }} key={index}>
            <CCard style={{
              backgroundColor: item.backgroundColor
            }} className="text-white text-center" onClick={() => {
              history.push(item.to)
            }}>
              <CCardBody className="card-wrapper">
                <div className="circle-icon" style={{
                  backgroundColor: item.backgroundColorCircle
                }}>
                  <span style={{ color: item.colorCircle }}>{item.number}</span>
                </div>
                <div className="note-card">
                  <span style={{ color: item.color }}>{item.title}</span>
                </div>
              </CCardBody>
            </CCard>
          </CCol>
        )
      }
    )
    return displayWibget
  }

  return (
    <CRow>
      {showWidget()}
    </CRow>
  )
}


