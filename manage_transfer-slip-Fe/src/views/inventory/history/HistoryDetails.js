import {
    CCard,
    CCardBody,
    CCardHeader,
    CCol,
    CFormGroup,
    CInput,
    CLabel,
    CRow,
    CButton,
    CDataTable,
    CSelect,
    CPagination,
} from '@coreui/react'
import Select from 'react-select' 
import React, { useState,useEffect} from 'react'
import { useHistory } from 'react-router'
import moment from 'moment'
import DateTimePicker from 'react-datetime-picker'
import axios from 'axios'



export default function HistoryDetails(props) {
    const history = useHistory();
    const [fromDate, setFromDate] = useState(null)
    const [toDate, setToDate] = useState(null)
    const id = props.location.state.id
    const [size, setSize] = useState(0)
    const [search, setSearch] = useState("")
    const user = JSON.parse(sessionStorage.user)
    const URL_HISTORY = "http://localhost:8080/admin/history?id="
    const URL_HISTORY_SZIE = "http://localhost:8080/admin/history/size?id="
    const SEARCH = "http://localhost:8080/transfers/history/search-by-key?page="
    const URL_SEARCH_SIZE = "http://localhost:8080/transfers/history/search-by-key/size?key="
    const FILTER_TIME_SIZE = "http://localhost:8080/transfers/history/finding/size?from="
    const URL_FILTER_TIME = "http://localhost:8080/transfers/history/finding?from="
    const [page, setPage] = useState({
        number: 1,
        limit: 10
    })
    const fields = [
        {key: "inventoriesDTOExport.code",label: "Mã kho chuyển"},
        {key: "inventoriesDTOExport.name",label: "Tên kho chuyển"},
        {key: "inventoriesDTOImport.code",label:"Mã kho nhận"},
        {key: "inventoriesDTOImport.name",label:"Tên kho nhận"},
        {key: "transferDTO.createAt",label:"Ngày tạo"},
        {key: "transferDTO.movingAt",label:"Ngày chuyển"},
        {key: "transferDTO.finishAt",label:"Ngày hoàn thành"},
        {key: "transferDTO.deletedAt",label:"Ngày hủy"},
        {key: "transferDTO.status",label:"Trạng thái"}

    ]
    const [data, setData] = useState([])
    useEffect(() => {
        loadData()
        loadSize()
    }, [page,search,toDate,fromDate])

    async function loadData() {
        var url
        if ( (search == "") && (fromDate == null) && (toDate == null)) {
            url = URL_HISTORY + id + "&page=" + page.number + "&limit=" + page.limit
        }else{
            if (search != "") {
                url =SEARCH + page.number + "&limit=" + page.limit + "&key=" + search + "&id=" + id
            }
            else{
                if ( (fromDate != null) && (toDate == null))  {
                    url = URL_FILTER_TIME + toStringDate(fromDate) + "&to=" + toStringDate(new Date) + "&page=" + page.number + "&limit=" + page.limit + "&id=" + id
                }
                else{
                    if ((toDate != null) && (fromDate == null)) {
                        url = URL_FILTER_TIME + toStringDate(toDate) + "&to=" + toStringDate(new Date) + "&page=" + page.number + "&limit=" + page.limit + "&id=" + id

                    }
                    else{
                        url = URL_FILTER_TIME + toStringDate(fromDate) + "&to=" + toStringDate(toDate) + "&page=" + page.number + "&limit=" + page.limit + "&id=" + id

                    }

                 }
            }
        }

        console.log("data url: ",url);
        await axios.get(url,{
            headers:{
                    "Authorization": user.type + " " + user.token
                }
        }).then(
            (res) => {
              
                if (res.status == 200) {
                    console.log("data: ",res.data)
                   if (res.data  == []) {
                       console.log("here if")
                       setData([])
                   }else{
                       console.log("here else")
                    setData(res.data)
                   }
                }
            }
        ).catch(console.log )
    }
   async function loadSize(){
        var url 
        if ( (search == "") && (fromDate == null) && (toDate == null)) {
            url = URL_HISTORY_SZIE + id
        }else{
            if (search != "") {
                url =URL_SEARCH_SIZE + search+ "&id=" + id
            }
            else{
                if ((fromDate != null) && (toDate == null)) {
                    url = FILTER_TIME_SIZE + toStringDate(fromDate) + "&to=" + toStringDate(new Date) + "&id=" + id
                }else{
                    if ((toDate != null) && (fromDate == null)) {
                        url = FILTER_TIME_SIZE + toStringDate(toDate) + "&to=" + toStringDate(new Date) + "&id=" + id

                    }
                    else{
                        if((toDate == null) && (fromDate != null)){
                            url = FILTER_TIME_SIZE + toStringDate(fromDate) + "&to=" + toStringDate(new Date) + "&id=" + id

                        }else{
                            url = FILTER_TIME_SIZE + toStringDate(fromDate) + "&to=" + toStringDate(toDate) + "&id=" + id
                        }
                    }
                   
                }
            }
        }
        console.log("size url: ",url)
        await axios.get(url,{
            headers:{
                    "Authorization": user.type + " " + user.token
                }
        }).then(
            (res) => {
              
                if (res.status == 200) {
                    console.log(size)
                    setSize(res.data)
                }
            }
        ).catch(console.log )
    }

    function formatDate(e) {
        if (e == null) {
            return ""
        }
        return moment(e).format('YYYY/MM/DD')
    }

    function setNumberPage(e) {
        if(e == 0){
            e = 1
        }
        setPage(
         {
             number: e,
             limit: page.limit
         }
     )
     }
   
    function reset(e) {
        e.preventDefault()
        setFromDate(null)
        setToDate(null)
      }
    function toStringDate(e) {
        return e.getFullYear() + "-" +  (e.getMonth() + 1)+ "-" + e.getDate()
      }
      function onChangeFrom(e) {
    
          
            setFromDate(e)
        
      }
      function onChangeTo(e) {
     
        setToDate(e)
    
      }
      function gotoTransfer(e) {
          const url = "/transfers/" + e.transferDTO.id
          history.push(url)
      }
     
  return (
    <>
           <CRow>
                <CCol xs="12" sm="12">
                    <div className="mb-3" style={{ cursor: "pointer" }} onClick={() =>{
                        props.history.goBack();
                    } }>  Quay lại </div>
                    <div className="mb-6" style={{ color: "#686868" }} > <h2><b>Lịch sử kho {id}</b></h2> </div>
                </CCol>
            </CRow>
            <CRow>
                <CCol xs="12" sm="12">
                </CCol>
            </CRow>
            <CRow className="mt-2">

                <CCol xs="12" sm="12">
                    <CCard>
                        <CCardHeader>
                            <CRow>
                                <CCol md='4' className="text-left">
                                    <span>Lịch sử nhập xuất kho</span>
                                </CCol>
                            </CRow>
                        </CCardHeader>
                        <CCardBody>
                            <CCardBody className="">
                                <CRow className="">
                                    <CCol md='4'>
                                        <CFormGroup className="d-flex justify-content-between">
                                            <CLabel htmlFor="search" className="w-25 pt-2">Tìm kiếm:</CLabel>

                                            <CInput id="search" name="search" placeholder="Nhập từ khóa..." onChange={(e) => {
                                                if (e == "") {
                                                    setSearch("")
                                                }else{
                                                    setSearch(e.target.value)
                                                }
                                            }} maxLength="200" />
                                        </CFormGroup>
                                    </CCol>
                                    <CCol md='3' className="d-flex justify-content-end">
                                        <CFormGroup>
                                            <CLabel htmlFor="from" className="w-20 pt-2 mr-2" >Từ:</CLabel>
                                            <DateTimePicker
                                                id="from"
                                                selected={fromDate}
                                                value={fromDate}
                                                onChange={onChangeFrom}
                                                format='d-MM-y'
                                                dateFormat='true'
                                                disableClock
                                                yearPlaceholder="yyyy"
                                                dayPlaceholder="dd"
                                                monthPlaceholder="mm"
                                                maxDate={new Date()}
                                            />
                                        </CFormGroup>
                                   
                                    </CCol>
                                    <CCol md='3' className="text-right">
                                        <CFormGroup>
                                            <CLabel htmlFor="toDate" className="w-20 pt-2 mr-2">Đến: </CLabel>

                                            <DateTimePicker id="toDate"
                                                selected={toDate}
                                                value={toDate}
                                                onChange={onChangeTo}
                                                maxDate={new Date()}
                                                format='d-MM-y'
                                                disableClock
                                                yearPlaceholder="yyyy"
                                                dayPlaceholder="dd"
                                                monthPlaceholder="mm"
                                            />
                                        </CFormGroup>
                                    </CCol>
                                    <CCol md="2">
                                        <CButton className="btn-secondary" onClick={reset}>
                                            Đặt lại
                                        </CButton>
                                    </CCol>
                                </CRow>

                                <CDataTable
                                    
                                    items={data}
                                    size="sx"
                                    hover
                                    onRowClick={
                                        gotoTransfer
                                    }
                                    fields={fields}
                                     scopedSlots={{
                                         'inventoriesDTOExport.code': (item) => (
                                             <td>
                                                {item.inventoriesDTOExport.code}
                                             </td>
                                         ),
                                         'inventoriesDTOExport.name': (item) => (
                                             <td>
                                                 {item.inventoriesDTOExport.name}
                                             </td>
                                         ),
                                         'inventoriesDTOImport.code': (item) => (
                                             <td>{item.inventoriesDTOImport.code}</td>
                                         ),
                                         'inventoriesDTOImport.name': (item) => (
                                             <td>{item.inventoriesDTOImport.name}</td>
                                         ),
                                         'transferDTO.createAt': (item) => (
                                             <td>{formatDate(item.transferDTO.createAt)}</td>
                                         ),
                                         'transferDTO.movingAt': (item) => (
                                             <td>{formatDate(item.transferDTO.movingAt)}</td>
                                         ),
                                         'transferDTO.finishAt': (item) => (
                                             <td>{formatDate(item.transferDTO.finishAt)}</td>
                                         ),
                                         'transferDTO.deletedAt': (item) => (
                                             <td>{formatDate(item.transferDTO.deletedAt)}</td>
                                         ),
                                        'transferDTO.status': (item) => (
                                            <td>{item.transferDTO.status}</td>
                                        )
                                         
                                     }}
                                />
                                <CRow className="d-flex justify-content-between">
                                    <CCol md='5'>
                                        <CPagination
                                            activePage={page.number}
                                            pages={Math.ceil(size / page.limit)}
                                            onActivePageChange={setNumberPage}
                                        />
                                    </CCol>
                                    <CCol md="4">

                                    </CCol>
                                    <CCol md='3' className="text-right">
                                        <span>Số bản ghi: </span>
                                        <CSelect custom name="rows" id="limit" style={{ width: '100px' }} onChange>
                                            <option value="10">10</option>
                                            <option value="20">20</option>
                                            <option value="30">30</option>
                                            <option value="50">50</option>
                                        </CSelect>
                                    </CCol>

                                </CRow>
                            </CCardBody>

                        </CCardBody>
                    </CCard>
                </CCol>

            </CRow>
            <CRow className="mb-2">


                <CCol xs="12" sm="2">
                    <CButton
                        color="primary"
                        aria-pressed="true"
                        onClick={
                            () => {
                                history.push("/statistic/import")
                            }
                        }
                        className="btn btn-primary px-4"
                    >
                    Báo Cáo
                    </CButton>
                </CCol>
                <CCol xs="12" sm="8"></CCol>
                <CCol xs="12" sm="1">
                    <CButton

                        color="dark"
                        aria-pressed="true"
                        onClick={
                            () => {
                                props.history.goBack()
                            }
                        }
                    >
                        Quay lại
                    </CButton>

                </CCol>

               
               
            </CRow>

    </>
  )
}


